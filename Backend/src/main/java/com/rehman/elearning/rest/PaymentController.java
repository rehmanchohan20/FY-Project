package com.rehman.elearning.rest;

import com.rehman.elearning.constants.PaymentStatus;
import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.rehman.elearning.service.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            PaymentResponseDTO responseDTO = paymentService.createAndProcessPayment(paymentRequestDTO);
            return ResponseEntity.ok(responseDTO); // Return 200 OK with response
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing payment: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching exchange rate: " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam("payment_intent") String paymentIntentId) {
        try {
            PaymentStatus status = paymentService.updatePaymentStatus(paymentIntentId);
            return ResponseEntity.ok("Payment Success! Payment ID: " + paymentIntentId + " Status: " + status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Payment processing failed: " + e.getMessage());
        }
    }

    @GetMapping("/failure")
    public ResponseEntity<String> paymentFailure() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed. Please try again.");
    }
}