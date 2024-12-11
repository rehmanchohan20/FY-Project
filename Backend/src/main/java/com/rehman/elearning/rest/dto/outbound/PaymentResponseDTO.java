package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.constants.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

public class PaymentResponseDTO {

    private Long id;                     // Payment ID
    private String transactionId;          // Transaction ID
    private Double amount;               // Payment amount
    private PaymentStatus status;        // Payment status
    private Long courseId;               // Course ID related to the payment
    private Long studentId;              // Student ID making the payment
    private Map<String, String> paymentDetails; // Additional details (including the URL for Stripe Checkout)

    // Constructor without additional payment details
    public PaymentResponseDTO(Long id, String transactionId, Double amount, PaymentStatus status,
                              Long courseId, Long studentId) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.courseId = courseId;
        this.studentId = studentId;
        this.paymentDetails = new HashMap<>(); // Initialize the map
    }

    // Constructor with additional payment details (including checkout URL)
    public PaymentResponseDTO(Long id, String transactionId, Double amount, PaymentStatus status,
                              Long courseId, Long studentId, String checkoutUrl) {
        this(id, transactionId, amount, status, courseId, studentId);
        this.paymentDetails = new HashMap<>();
        this.paymentDetails.put("checkoutUrl", checkoutUrl); // Add checkout URL to paymentDetails
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Map<String, String> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Map<String, String> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
}
