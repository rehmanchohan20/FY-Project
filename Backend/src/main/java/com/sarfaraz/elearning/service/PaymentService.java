package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    // Create a payment for a specific course
    PaymentResponseDTO createPayment(Long courseId, PaymentRequestDTO paymentRequestDto);

    // Update a payment for a specific course
    PaymentResponseDTO updatePayment(Long courseId, Long id, PaymentRequestDTO paymentRequestDto);

    // Get a specific payment by courseId and paymentId
    PaymentResponseDTO getPaymentById(Long courseId, Long id);

    // Get all payments for a specific course
    List<PaymentResponseDTO> getAllPaymentsForCourse(Long courseId);

    // Delete a payment for a specific course
    void deletePayment(Long courseId, Long id);
}
