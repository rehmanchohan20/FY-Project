package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.sarfaraz.elearning.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @PathVariable Long courseId,
            @RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO payment = paymentService.createPayment(courseId, paymentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable Long courseId,
            @PathVariable Long id) {
        PaymentResponseDTO payment = paymentService.getPaymentById(courseId, id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPaymentsForCourse(
            @PathVariable Long courseId) {
        List<PaymentResponseDTO> payments = paymentService.getAllPaymentsForCourse(courseId);
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable Long courseId,
            @PathVariable Long id,
            @RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO payment = paymentService.updatePayment(courseId, id, paymentRequestDTO);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long courseId,
            @PathVariable Long id) {
        paymentService.deletePayment(courseId, id);
        return ResponseEntity.noContent().build();
    }
}
