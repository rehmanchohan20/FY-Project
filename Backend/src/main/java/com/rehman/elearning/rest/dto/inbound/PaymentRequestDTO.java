package com.rehman.elearning.rest.dto.inbound;

import com.rehman.elearning.model.Amount;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Student;

public class PaymentRequestDTO {
    private Amount amount;         // Amount of the payment
    private Student studentId;     // ID of the student making the payment
    private Course courseId;       // ID of the course associated with the payment

    // Getters and Setters
    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student studentId) {
        this.studentId = studentId;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "PaymentRequestDTO{" +
                ", amount=" + amount +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
