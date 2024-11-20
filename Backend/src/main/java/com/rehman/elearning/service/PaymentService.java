package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO createPayment(Long courseId, PaymentRequestDTO paymentRequestDto);
    PaymentResponseDTO verifyPayment(String transactionId);
    PaymentResponseDTO updatePayment(Long courseId, Long id, PaymentRequestDTO paymentRequestDto);
    PaymentResponseDTO getPaymentById(Long courseId, Long id);
    List<PaymentResponseDTO> getAllPaymentsForCourse(Long courseId);
    void deletePayment(Long courseId, Long id);
}
