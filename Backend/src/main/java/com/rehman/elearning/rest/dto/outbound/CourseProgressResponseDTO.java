package com.rehman.elearning.rest.dto.outbound;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class CourseProgressResponseDTO {
    private Long id;
    private Long studentId;
    private Long courseId;
    private double progressPercentage;
    private Timestamp lastStudiedAt; // Timestamp for the last time the student studied


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Timestamp getLastStudiedAt() {
        return lastStudiedAt;
    }

    public void setLastStudiedAt(Timestamp lastStudiedAt) {
        this.lastStudiedAt = lastStudiedAt;
    }
}