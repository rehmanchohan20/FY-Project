package com.rehman.elearning.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
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
    private Timestamp createdAt;  // Use LocalDate Time instead of Date

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp  createdAt) {
        this.createdAt = createdAt;
    }
}
