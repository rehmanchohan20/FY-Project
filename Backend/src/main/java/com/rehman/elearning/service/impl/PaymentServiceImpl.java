package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.service.PaymentService;
import com.rehman.elearning.constants.PaymentStatus;
import com.rehman.elearning.model.Payment;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.PaymentRepository;
import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.failure.url}")
    private String failureUrl;

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

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
                                                                .setName(course.getTitle())  // Course name as the product
                                                                .build()
                                                )
                                                .setUnitAmount((long) (amountInUSD * 100))  // Amount in cents
                                                .build()
                                )
                                .setQuantity(1L)
                                .build()
                )
                .setMode(SessionCreateParams.Mode.PAYMENT)  // Payment mode
                .setSuccessUrl(successUrl)  // Redirect URL after successful payment
                .setCancelUrl(failureUrl)  // Redirect URL after payment failure
                .build();

        // Create the session with Stripe API
        Session session = Session.create(checkoutSessionParams);

        // Persist payment details in your database
        Payment payment = new Payment();
        payment.setTransactionId(session.getId());  // Store the session ID as a String
        payment.setAmount(amountInPKR); // Store the original amount in PKR
        payment.setStatus(PaymentStatus.PENDING);  // Status is pending as payment is not yet confirmed
        payment.setCreatedBy(UserCreatedBy.Self);
        payment.setCourse(course);
        payment.setStudent(student);

        Payment savedPayment = paymentRepository.save(payment);

        // Return response DTO with the checkout URL for the frontend
        return new PaymentResponseDTO(
                savedPayment.getId(),
                savedPayment.getTransactionId(),
                savedPayment.getAmount(),
                savedPayment.getStatus(),
                savedPayment.getCourse().getId(),
                savedPayment.getStudent().getUserId(),
                session.getUrl()  // Send the checkout session URL to the frontend
        );
    }


    private double fetchExchangeRatePKRtoUSD() throws IOException {
        // Example hardcoded value, replace with an actual API call if needed
        return 300.0; // Assume 1 USD = 300 PKR
    }

    @Override
    public PaymentStatus updatePaymentStatus(String paymentIntentId) throws StripeException {
        Stripe.apiKey = secretKey;

        // Retrieve PaymentIntent
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        // Check the payment status and return appropriate status
        switch (paymentIntent.getStatus()) {
            case "succeeded":
                return PaymentStatus.SUCCESS;
            case "requires_payment_method":
                return PaymentStatus.FAILED;
            case "requires_confirmation":
            default:
                return PaymentStatus.PENDING;
        }
    }

    @Override
    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    public String getFailureUrl() {
        return failureUrl;
    }
}
