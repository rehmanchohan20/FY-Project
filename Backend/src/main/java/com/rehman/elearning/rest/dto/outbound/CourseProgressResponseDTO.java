package com.rehman.elearning.rest.dto.outbound;

import java.time.LocalDateTime;

public class CourseProgressResponseDTO {
    private Long id;
    private Long studentId;
    private Long courseModuleLessonId;
    private double progressPercentage;
    private LocalDateTime lastStudiedAt; // Timestamp for the last time the student studied

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

    public Long getCourseModuleLessonId() {
        return courseModuleLessonId;
    }

    public void setCourseModuleLessonId(Long courseModuleLessonId) {
        this.courseModuleLessonId = courseModuleLessonId;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public LocalDateTime getLastStudiedAt() {
        return lastStudiedAt;
    }

    public void setLastStudiedAt(LocalDateTime lastStudiedAt) {
        this.lastStudiedAt = lastStudiedAt;
    }
}
