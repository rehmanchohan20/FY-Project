package com.rehman.elearning.rest.dto.outbound;

import java.util.Set;

public class StudentResponseDTO {
    private Long userId;
    private UserResponseDTO user;
    private Set<CourseResponseDTO> courses;
    private Set<CourseProgressResponseDTO> courseProgress;
    private Set<PaymentResponseDTO> payments;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public Set<CourseResponseDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseResponseDTO> courses) {
        this.courses = courses;
    }

    public Set<CourseProgressResponseDTO> getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(Set<CourseProgressResponseDTO> courseProgress) {
        this.courseProgress = courseProgress;
    }

    public Set<PaymentResponseDTO> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentResponseDTO> payments) {
        this.payments = payments;
    }
}
