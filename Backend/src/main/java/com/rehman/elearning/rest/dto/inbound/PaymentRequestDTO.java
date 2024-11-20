package com.rehman.elearning.rest.dto.inbound;

import java.math.BigDecimal;

public class PaymentRequestDTO {

    private String transactionId;  // Transaction ID for the payment
    private Double amount;     // Amount of the payment
    private Long studentId;        // ID of the student making the payment
    private String paymentStatus;  // Payment status (if updatable in the request)

    // Getters and setters
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
