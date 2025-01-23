package com.rehman.elearning.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "course_enrollment_data")
public class CourseEnrollmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrollment_count")
    private Long enrollmentCount;

    @Column(name = "created_at")
    private LocalDate createdAt;  // Use LocalDate Time instead of Date

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getEnrollmentCount() {
        return enrollmentCount;
    }

    public void setEnrollmentCount(Long enrollmentCount) {
        this.enrollmentCount = enrollmentCount;
    }

    public LocalDate  getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate  createdAt) {
        this.createdAt = createdAt;
    }
}
