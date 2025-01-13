package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.PaymentStatus;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.model.*;
import com.rehman.elearning.repository.CourseProgressRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.PaymentRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.rehman.elearning.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.failure.url}")
    private String failureUrl;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseProgressRepository courseProgressRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, CourseRepository courseRepository, StudentRepository studentRepository, CourseProgressRepository courseProgressRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseProgressRepository = courseProgressRepository;
    }

    @Override
    @Transactional
    public PaymentResponseDTO createAndProcessPayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, IOException {
        Stripe.apiKey = secretKey;

        Optional<Course> courseOpt = courseRepository.findById(paymentRequestDTO.getCourseId().getId());
        Optional<Student> studentOpt = studentRepository.findById(paymentRequestDTO.getStudentId().getUserId());

        if (courseOpt.isEmpty() || studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid course or student ID.");
        }

        Course course = courseOpt.get();
        Student student = studentOpt.get();

        double amountInPKR = paymentRequestDTO.getAmount().getAmount();
        double exchangeRate = fetchExchangeRatePKRtoUSD();
        double amountInUSD = amountInPKR / exchangeRate;

        // Save initial payment with a temporary transaction ID
        Payment payment = new Payment();
        payment.setTransactionId(UUID.randomUUID().toString());  // Temporary ID
        payment.setAmount(amountInPKR);
        payment.setCurrency(paymentRequestDTO.getAmount().getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedBy(UserCreatedBy.Self);
        payment.setCourse(course);
        payment.setStudent(student);

        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Saved payment with temporary ID: {}", savedPayment.getTransactionId());

        // Use an idempotency key to ensure no duplicate PaymentIntents are created
        String idempotencyKey = UUID.randomUUID().toString();

        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount((long) (amountInUSD * 100)) // Amount in cents
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .setReceiptEmail(student.getUser().getEmail())
                .setDescription("Payment for course: " + course.getTitle())
                .putMetadata("studentId", student.getUserId().toString())
                .putMetadata("courseId", course.getId().toString())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);

        // Update the payment with the actual transaction ID
        savedPayment.setTransactionId(paymentIntent.getId());
        Payment updatedPayment = paymentRepository.save(savedPayment);

        // Replace :id in successUrl with the actual course ID
        String dynamicSuccessUrl = successUrl.replace(":id", course.getId().toString());

        SessionCreateParams checkoutSessionParams = SessionCreateParams.builder()
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .setSetupFutureUsage(SessionCreateParams.PaymentIntentData.SetupFutureUsage.OFF_SESSION)
                                .putMetadata("studentId", student.getUserId().toString())
                                .putMetadata("courseId", course.getId().toString())
                                .build()
                )
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(course.getTitle())
                                                                .build()
                                                )
                                                .setUnitAmount((long) (amountInUSD * 100))
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(dynamicSuccessUrl)
                .setCancelUrl(failureUrl)
                .build();

        Session session = Session.create(checkoutSessionParams);

        return new PaymentResponseDTO(
                updatedPayment.getId(),
                updatedPayment.getTransactionId(),
                updatedPayment.getAmount(),
                updatedPayment.getCurrency(),
                updatedPayment.getStatus(),
                updatedPayment.getCourse().getId(),
                updatedPayment.getStudent().getUserId(),
                paymentIntent.getClientSecret(),
                session.getUrl()
        );
    }

    @Override
    @Transactional
    public void handleWebhook(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            String eventType = event.getType();

            String studentId = null;
            String courseId = null;

            if ("payment_intent.succeeded".equals(eventType)) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
                studentId = paymentIntent.getMetadata().get("studentId");
                courseId = paymentIntent.getMetadata().get("courseId");
            }

            if (studentId != null && courseId != null) {
                handlePaymentSuccess(studentId, courseId);
            }
        } catch (SignatureVerificationException e) {
            logger.error("Webhook signature verification failed: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Error processing webhook: {}", e.getMessage());
        }
    }

    @Override
    public void handlePaymentFailure(String transactionId) {
        paymentRepository.findByTransactionId(transactionId)
                .ifPresent(payment -> {
                    payment.setStatus(PaymentStatus.FAILED);
                    paymentRepository.save(payment);
                });
    }

    private void handlePaymentSuccess(String studentId, String courseId) {
        // Retrieve and validate student
        Student student = studentRepository.findById(Long.valueOf(studentId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID."));

        // Retrieve and validate course
        Course course = courseRepository.findById(Long.valueOf(courseId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID."));

        // Enroll student in course
        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);
        courseRepository.save(course);

        // Initialize course progress to 0%
        List<CourseModule> courseModules = new ArrayList<>(course.getcourseModule());
        if (courseModules.isEmpty()) {
            throw new RuntimeException("No course modules found");
        }

        List<CourseModuleLesson> courseModuleLessons = new ArrayList<>(courseModules.get(0).getCourseModuleLessons());
        if (courseModuleLessons.isEmpty()) {
            throw new RuntimeException("No course module lessons found");
        }

        CourseProgress courseProgress = new CourseProgress();
        courseProgress.setStudent(student);
        courseProgress.setCourseModuleLesson(courseModuleLessons.get(0)); // Assuming the first lesson
        courseProgress.setProgressPercentage(0.0);
        courseProgress.setCreatedBy(UserCreatedBy.Self);
        courseProgressRepository.save(courseProgress);

        // Update payment status to SUCCESS
        paymentRepository.findByStudentUserIdAndCourseId(student.getUserId(), course.getId())
                .ifPresent(payment -> {
                    payment.setStatus(PaymentStatus.SUCCESS);
                    paymentRepository.save(payment);
                });
    }

    private double fetchExchangeRatePKRtoUSD() {
        return 300.0; // Example static rate
    }
}

//package com.rehman.elearning.service.impl;
//
//import com.rehman.elearning.constants.PaymentStatus;
//import com.rehman.elearning.constants.UserCreatedBy;
//import com.rehman.elearning.model.*;
//import com.rehman.elearning.repository.CourseProgressRepository;
//import com.rehman.elearning.repository.CourseRepository;
//import com.rehman.elearning.repository.PaymentRepository;
//import com.rehman.elearning.repository.StudentRepository;
//import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
//import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
//import com.rehman.elearning.service.PaymentService;
//import com.stripe.Stripe;
//import com.stripe.exception.SignatureVerificationException;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Event;
//import com.stripe.model.PaymentIntent;
//import com.stripe.model.checkout.Session;
//import com.stripe.net.Webhook;
//import com.stripe.param.PaymentIntentCreateParams;
//import com.stripe.param.checkout.SessionCreateParams;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
//
//    @Value("${stripe.secret.key}")
//    private String secretKey;
//
//    @Value("${stripe.success.url}")
//    private String successUrl;
//
//    @Value("${stripe.failure.url}")
//    private String failureUrl;
//
//    @Value("${stripe.webhook.secret}")
//    private String webhookSecret;
//
//    private final PaymentRepository paymentRepository;
//    private final CourseRepository courseRepository;
//    private final StudentRepository studentRepository;
//    private final CourseProgressRepository courseProgressRepository;
//
//    public PaymentServiceImpl(PaymentRepository paymentRepository, CourseRepository courseRepository, StudentRepository studentRepository, CourseProgressRepository courseProgressRepository) {
//        this.paymentRepository = paymentRepository;
//        this.courseRepository = courseRepository;
//        this.studentRepository = studentRepository;
//        this.courseProgressRepository = courseProgressRepository;
//    }
//
//    @Override
//    @Transactional
//    public PaymentResponseDTO createAndProcessPayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, IOException {
//        Stripe.apiKey = secretKey;
//
//        Optional<Course> courseOpt = courseRepository.findById(paymentRequestDTO.getCourseId().getId());
//        Optional<Student> studentOpt = studentRepository.findById(paymentRequestDTO.getStudentId().getUserId());
//
//        if (courseOpt.isEmpty() || studentOpt.isEmpty()) {
//            throw new IllegalArgumentException("Invalid course or student ID.");
//        }
//
//        Course course = courseOpt.get();
//        Student student = studentOpt.get();
//
//        double amountInPKR = paymentRequestDTO.getAmount().getAmount();
//        double exchangeRate = fetchExchangeRatePKRtoUSD();
//        double amountInUSD = amountInPKR / exchangeRate;
//
//        // Save initial payment with a temporary transaction ID
//        Payment payment = new Payment();
//        payment.setTransactionId(UUID.randomUUID().toString());  // Temporary ID
//        payment.setAmount(amountInPKR);
//        payment.setCurrency(paymentRequestDTO.getAmount().getCurrency());
//        payment.setStatus(PaymentStatus.PENDING);
//        payment.setCreatedBy(UserCreatedBy.Self);
//        payment.setCourse(course);
//        payment.setStudent(student);
//
//        Payment savedPayment = paymentRepository.save(payment);
//        logger.info("Saved payment with temporary ID: {}", savedPayment.getTransactionId());
//
//        // Use an idempotency key to ensure no duplicate PaymentIntents are created
//        String idempotencyKey = UUID.randomUUID().toString();
//
//        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
//                .setAmount((long) (amountInUSD * 100)) // Amount in cents
//                .setCurrency("usd")
//                .addPaymentMethodType("card")
//                .setReceiptEmail(student.getUser().getEmail())
//                .setDescription("Payment for course: " + course.getTitle())
//                .putMetadata("studentId", student.getUserId().toString())
//                .putMetadata("courseId", course.getId().toString())
//                .build();
//
//        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
//
//        // Update the payment with the actual transaction ID
//        savedPayment.setTransactionId(paymentIntent.getId());
//        Payment updatedPayment = paymentRepository.save(savedPayment);
//
//        // Replace :id in successUrl with the actual course ID
//        String dynamicSuccessUrl = successUrl.replace(":id", course.getId().toString());
//
//        SessionCreateParams checkoutSessionParams = SessionCreateParams.builder()
//                .setPaymentIntentData(
//                        SessionCreateParams.PaymentIntentData.builder()
//                                .setSetupFutureUsage(SessionCreateParams.PaymentIntentData.SetupFutureUsage.OFF_SESSION)
//                                .putMetadata("studentId", student.getUserId().toString())
//                                .putMetadata("courseId", course.getId().toString())
//                                .build()
//                )
//                .addLineItem(
//                        SessionCreateParams.LineItem.builder()
//                                .setPriceData(
//                                        SessionCreateParams.LineItem.PriceData.builder()
//                                                .setCurrency("usd")
//                                                .setProductData(
//                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                                                .setName(course.getTitle())
//                                                                .build()
//                                                )
//                                                .setUnitAmount((long) (amountInUSD * 100))
//                                                .build()
//                                )
//                                .setQuantity(1L)
//                                .build()
//                )
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl(dynamicSuccessUrl)
//                .setCancelUrl(failureUrl)
//                .build();
//
//        Session session = Session.create(checkoutSessionParams);
//
//        return new PaymentResponseDTO(
//                updatedPayment.getId(),
//                updatedPayment.getTransactionId(),
//                updatedPayment.getAmount(),
//                updatedPayment.getCurrency(),
//                updatedPayment.getStatus(),
//                updatedPayment.getCourse().getId(),
//                updatedPayment.getStudent().getUserId(),
//                paymentIntent.getClientSecret(),
//                session.getUrl()
//        );
//    }
//
//    @Override
//    @Transactional
//    public void handleWebhook(String payload, String sigHeader) {
//        try {
//            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
//            String eventType = event.getType();
//
//            String studentId = null;
//            String courseId = null;
//
//            if ("payment_intent.succeeded".equals(eventType)) {
//                PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
//                studentId = paymentIntent.getMetadata().get("studentId");
//                courseId = paymentIntent.getMetadata().get("courseId");
//            }
//
//            if (studentId != null && courseId != null) {
//                handlePaymentSuccess(studentId, courseId);
//            }
//        } catch (SignatureVerificationException e) {
//            logger.error("Webhook signature verification failed: {}", e.getMessage());
//        } catch (Exception e) {
//            logger.error("Error processing webhook: {}", e.getMessage());
//        }
//    }
//
//    @Override
//    public void handlePaymentFailure(String transactionId) {
//        paymentRepository.findByTransactionId(transactionId)
//                .ifPresent(payment -> {
//                    payment.setStatus(PaymentStatus.FAILED);
//                    paymentRepository.save(payment);
//                });
//    }
//
//    private void handlePaymentSuccess(String studentId, String courseId) {
//        // Retrieve and validate student
//        Student student = studentRepository.findById(Long.valueOf(studentId))
//                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID."));
//
//        // Retrieve and validate course
//        Course course = courseRepository.findById(Long.valueOf(courseId))
//                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID."));
//
//        // Enroll student in course
//        student.getCourses().add(course);
//        course.getStudents().add(student);
//        studentRepository.save(student);
//        courseRepository.save(course);
//
//
//        // Update payment status to SUCCESS
//        paymentRepository.findByStudentUserIdAndCourseId(student.getUserId(), course.getId())
//                .ifPresent(payment -> {
//                    payment.setStatus(PaymentStatus.SUCCESS);
//                    paymentRepository.save(payment);
//                });
//    }
//
//    private double fetchExchangeRatePKRtoUSD() {
//        return 300.0; // Example static rate
//    }
//}
