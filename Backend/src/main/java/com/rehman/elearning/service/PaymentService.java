package com.rehman.elearning.service;

import com.rehman.elearning.model.Payment;
import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.Optional;

public interface PaymentService {
    PaymentResponseDTO createAndProcessPayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, IOException;
//    Optional<Payment> findByTransactionId(String transactionId);
//    void save(Payment payment);
public void handleWebhook(String payload, String sigHeader);
//void enrollStudentInCourse(String transactionId);
//public void handlePaymentSuccess(String studentId, String courseId);
//    public void enrollStudentInCourse(Payment payment);
    void handlePaymentFailure(String transactionId);
}