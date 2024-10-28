package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.model.Payment;
import com.sarfaraz.elearning.repository.PaymentRepository;
import com.sarfaraz.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.sarfaraz.elearning.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDTO createPayment(Long courseId, PaymentRequestDTO paymentRequestDto) {
        Payment payment = new Payment();
        // Set properties from DTO to the entity
        payment.setId(courseId); // Set the courseId for the payment
        // Additional property mapping from paymentRequestDto to payment
        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDTO updatePayment(Long courseId, Long id, PaymentRequestDTO paymentRequestDto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        // Ensure the payment belongs to the given courseId, if needed
        // Update properties from DTO to the entity
        return mapToResponse(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long courseId, Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        // Optionally check if payment's courseId matches courseId parameter
        return mapToResponse(payment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPaymentsForCourse(Long courseId) {
        return paymentRepository.findAllByCourseId(courseId) // Assuming this method exists in PaymentRepository
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Long courseId, Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentResponseDTO mapToResponse(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        // Map properties from entity to DTO
        return dto;
    }
}
