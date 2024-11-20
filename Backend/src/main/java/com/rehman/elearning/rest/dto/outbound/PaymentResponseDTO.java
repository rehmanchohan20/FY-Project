package com.rehman.elearning.rest.dto.outbound;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class PaymentResponseDTO {

    private Long id;                     // Payment ID
    private String transactionId;        // Transaction ID
    private Double amount;           // Payment amount
    private String status;               // Payment status
    private LocalDateTime createdAt;     // Creation timestamp
    private LocalDateTime updatedAt;     // Update timestamp
    private Long courseId;               // Course ID related to the payment
    private Long studentId;              // Student ID making the payment
    private Map<String, String> paymentDetails; // Additional details (optional)

    // Constructor without additional payment details
    public PaymentResponseDTO(Long id, String transactionId, Double amount, String status,
                              LocalDateTime createdAt, LocalDateTime updatedAt,
                              Long courseId, Long studentId) {
        this.id = id;
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    // Constructor with additional payment details
    public PaymentResponseDTO(Long id, String transactionId, Double amount, String status,
                              LocalDateTime createdAt, LocalDateTime updatedAt,
                              Long courseId, Long studentId, Map<String, String> paymentDetails) {
        this(id, transactionId, amount, status, createdAt, updatedAt, courseId, studentId);
        this.paymentDetails = paymentDetails;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
