package com.rehman.elearning.service.impl;

// Import statements...

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
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.Optional;

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

        // Retrieve course and student entities
        Optional<Course> courseOpt = courseRepository.findById(paymentRequestDTO.getCourseId().getId());
        Optional<Student> studentOpt = studentRepository.findById(paymentRequestDTO.getStudentId().getUserId());

        if (courseOpt.isEmpty() || studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid course or student ID.");
        }

        Course course = courseOpt.get();
        Student student = studentOpt.get();

        // Convert PKR to USD
        double amountInPKR = paymentRequestDTO.getAmount().getAmount();
        double exchangeRate = fetchExchangeRatePKRtoUSD();
        double amountInUSD = amountInPKR / exchangeRate;

        // Generate Checkout Session
        SessionCreateParams checkoutSessionParams = SessionCreateParams.builder()
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
        logger.info("Created Stripe session with ID: {}", session.getId());

        // Persist payment details
        Payment payment = new Payment();
        payment.setTransactionId(session.getId());
        payment.setAmount(amountInPKR);
        payment.setCurrency(paymentRequestDTO.getAmount().getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedBy(UserCreatedBy.Self);
        payment.setCourse(course);
        payment.setStudent(student);

        Payment savedPayment = paymentRepository.save(payment);
        logger.info("Saved payment with ID: {}", savedPayment.getId());

        return new PaymentResponseDTO(
                savedPayment.getId(),
                savedPayment.getTransactionId(),
                savedPayment.getAmount(),
                savedPayment.getCurrency(),
                savedPayment.getStatus(),
                savedPayment.getCourse().getId(),
                savedPayment.getStudent().getUserId(),
                session.getUrl()
        );
    }

    @Override
    @Transactional
    public void handlePaymentSuccess(String transactionId) {
        logger.info("Handling payment success for transaction ID: {}", transactionId);

        // Fetch the payment record
        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(transactionId);

        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();

            // Update the payment status to SUCCESS
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment); // Ensure the status update is persisted
            logger.info("Payment status updated to SUCCESS for transaction ID: {}", transactionId);

            // Proceed to enroll the student in the course
            enrollStudentInCourse(transactionId);
        } else {
            logger.error("Payment not found for transaction ID: {}", transactionId);
            throw new IllegalArgumentException("Payment not found for transaction ID: " + transactionId);
        }
    }

    @Override
    @Transactional
    public void handlePaymentFailure(String transactionId) {
        logger.info("Handling payment failure for transaction ID: {}", transactionId);
        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(transactionId);

        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            logger.info("Payment status updated to FAILED for transaction ID: {}", transactionId);
        } else {
            logger.error("Payment not found for transaction ID: {}", transactionId);
        }
    }

    @Override
    @Transactional
    public void enrollStudentInCourse(String transactionId) {
        logger.info("Enrolling student for transaction ID: {}", transactionId);
        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(transactionId);

        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            Course course = payment.getCourse();
            Student student = payment.getStudent();

            logger.info("Enrolling student [{}] in course [{}]", student.getUserId(), course.getId());

            // Add course to the student's course list if not already enrolled
            if (!student.getCourses().contains(course)) {
                student.getCourses().add(course);
                logger.info("Added course [{}] to student's [{}] course list", course.getId(), student.getUserId());
            } else {
                logger.warn("Student [{}] is already enrolled in course [{}]", student.getUserId(), course.getId());
            }

            // Add student to the course's student list if not already added
            if (!course.getStudents().contains(student)) {
                course.getStudents().add(student);
                logger.info("Added student [{}] to course's [{}] student list", student.getUserId(), course.getId());
            } else {
                logger.warn("Student [{}] is already listed in course [{}]", student.getUserId(), course.getId());
            }

            // Ensure both the student and course are saved
            studentRepository.save(student);
            courseRepository.save(course);

            // Persist the payment status (optional, if needed)
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            logger.info("Student [{}] successfully enrolled in course [{}]", student.getUserId(), course.getId());
        } else {
            logger.error("Payment not found for transaction ID: {}", transactionId);
            throw new IllegalArgumentException("Payment not found for transaction ID: " + transactionId);
        }
    }

    private double fetchExchangeRatePKRtoUSD() throws IOException {
        return 300.0; // Assume 1 USD = 300 PKR
    }

    @Override
    @Transactional
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        logger.info("Received webhook event");
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            logger.error("Invalid signature", e);
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        String eventType = event.getType();
        logger.info("Processing event type: {}", eventType);

        try {
            // Deserialize the event data object
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;

            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                logger.error("Failed to deserialize event data. Possible API version mismatch.");
                return ResponseEntity.badRequest().body("Failed to deserialize event data");
            }

            // Process the event based on its type
            switch (eventType) {
                case "checkout.session.completed":
                    if (stripeObject instanceof Session session) {
                        handleCheckoutSessionCompleted(session);
                    }
                    break;

                case "payment_intent.payment_failed":
                    if (stripeObject instanceof PaymentIntent paymentIntent) {
                        handlePaymentIntentFailed(paymentIntent);
                    }
                    break;

                case "payment_intent.succeeded":
                    if (stripeObject instanceof PaymentIntent paymentIntent) {
                        handlePaymentIntentSucceeded(paymentIntent);
                    }
                    break;

                case "charge.succeeded":
                    if (stripeObject instanceof Charge charge) {
                        handleChargeSucceeded(charge);
                    }
                    break;

                case "payment_intent.created":
                    if (stripeObject instanceof PaymentIntent paymentIntent) {
                        handlePaymentIntentCreated(paymentIntent);
                    }
                    break;

                case "mandate.updated":
                    if (stripeObject instanceof Mandate mandate) {
                        handleMandateUpdated(mandate);
                    }
                    break;

                case "charge.updated":
                    if (stripeObject instanceof Charge charge) {
                        handleChargeUpdated(charge);
                    }
                    break;

                default:
                    logger.warn("Unhandled event type: {}", eventType);
                    break;
            }
        } catch (Exception e) {
            logger.error("Error processing webhook event: {}", eventType, e);
            return ResponseEntity.internalServerError().body("Error processing webhook event");
        }

        return ResponseEntity.ok("Event processed");
    }


    private <T> void processEvent(Event event, java.util.function.Consumer<T> handler) {
        Optional<Object> dataObject = event.getDataObjectDeserializer().getObject().map(obj -> (Object) obj);

        if (dataObject.isPresent()) {
            try {
                @SuppressWarnings("unchecked")
                T castedObject = (T) dataObject.get();
                handler.accept(castedObject);
            } catch (ClassCastException e) {
                logger.error("Failed to cast event data object", e);
            }
        } else {
            logger.error("Failed to deserialize event data");
        }
    }

    private void handleCheckoutSessionCompleted(Session session) {
        String transactionId = session.getId();
        logger.info("Processing checkout.session.completed for transaction ID: {}", transactionId);
        handlePaymentSuccess(transactionId);
    }

    private void handlePaymentIntentFailed(PaymentIntent paymentIntent) {
        String transactionId = paymentIntent.getId();
        logger.info("Processing payment_intent.payment_failed for transaction ID: {}", transactionId);
        handlePaymentFailure(transactionId);
    }

    private void handlePaymentIntentSucceeded(PaymentIntent paymentIntent) {
        String transactionId = paymentIntent.getId();
        logger.info("Processing payment_intent.succeeded for transaction ID: {}", transactionId);
        handlePaymentSuccess(transactionId);
    }

    private void handleChargeSucceeded(Charge charge) {
        String paymentIntentId = charge.getPaymentIntent();
        logger.info("Processing charge.succeeded for payment intent: {}", paymentIntentId);
        if (paymentIntentId != null) {
            handlePaymentSuccess(paymentIntentId);
        }
    }

    private void handlePaymentIntentCreated(PaymentIntent paymentIntent) {
        String transactionId = paymentIntent.getId();
        logger.info("Handling payment_intent.created event for transaction ID: {}", transactionId);
        // Add any specific logic needed for payment intent creation
    }

    private void handleMandateUpdated(Mandate mandate) {
        String mandateId = mandate.getId();
        logger.info("Handling mandate.updated event for mandate ID: {}", mandateId);
        // Add any specific logic needed for mandate updates
    }

    private void handleChargeUpdated(Charge charge) {
        String chargeId = charge.getId();
        logger.info("Handling charge.updated event for charge ID: {}", chargeId);
        // Add any specific logic needed for charge updates
    }
}