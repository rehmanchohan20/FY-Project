package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.PaymentStatus;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Payment;
import com.rehman.elearning.model.Student;
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

    public PaymentServiceImpl(PaymentRepository paymentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public PaymentResponseDTO createAndProcessPayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, IOException {
        Stripe.apiKey = secretKey;

        // Fetch course and student using IDs
        Course course = courseRepository.findById(paymentRequestDTO.getCourseId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID."));
        Student student = studentRepository.findById(paymentRequestDTO.getStudentId().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID."));

        double amountInPKR = paymentRequestDTO.getAmount().getAmount();
        double exchangeRate = fetchExchangeRatePKRtoUSD();
        double amountInUSD = amountInPKR / exchangeRate;

        // Save initial payment with temporary transaction ID
        Payment payment = new Payment();
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setAmount(amountInPKR);
        payment.setCurrency(paymentRequestDTO.getAmount().getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedBy(UserCreatedBy.Self);
        payment.setCourse(course);
        payment.setStudent(student);
        Payment savedPayment = paymentRepository.save(payment);

        // Create PaymentIntent on Stripe
        PaymentIntentCreateParams paymentIntentCreateParams = PaymentIntentCreateParams.builder()
                .setAmount((long) (amountInUSD * 100))
                .setCurrency("usd")
                .addPaymentMethodType("card")
                .setReceiptEmail(student.getUser().getEmail())
                .setDescription("Payment for course: " + course.getTitle())
                .putMetadata("studentId", student.getUserId().toString())
                .putMetadata("courseId", course.getId().toString())
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
        savedPayment.setTransactionId(paymentIntent.getId());
        paymentRepository.save(savedPayment);

        // Create Checkout Session for Stripe
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
                .setSuccessUrl(successUrl)
                .setCancelUrl(failureUrl)
                .build();

        Session session = Session.create(checkoutSessionParams);

        return new PaymentResponseDTO(
                savedPayment.getId(),
                savedPayment.getTransactionId(),
                savedPayment.getAmount(),
                savedPayment.getCurrency(),
                savedPayment.getStatus(),
                savedPayment.getCourse().getId(),
                savedPayment.getStudent().getUserId(),
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
        Student student = studentRepository.findById(Long.valueOf(studentId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID."));
        Course course = courseRepository.findById(Long.valueOf(courseId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID."));

        student.getCourses().add(course);
        course.getStudents().add(student);

        studentRepository.save(student);
        courseRepository.save(course);

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
