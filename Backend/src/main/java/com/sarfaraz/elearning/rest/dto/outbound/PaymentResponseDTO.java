package com.sarfaraz.elearning.rest.dto.outbound;

public class PaymentResponseDTO {
    private Long id;
    private String paymentStatus;  // E.g., 'PAID', 'PENDING'
    private Double amount;
    private CourseResponseDTO course;
    private StudentResponseDTO student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CourseResponseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseResponseDTO course) {
        this.course = course;
    }

    public StudentResponseDTO getStudent() {
        return student;
    }

    public void setStudent(StudentResponseDTO student) {
        this.student = student;
    }
}
