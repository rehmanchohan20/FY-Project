package com.rehman.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "top_courses")
public class TopCourses extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Long enrollments;
    private Double rating;

    // Getters and Setters
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

    public Long getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Long enrollments) {
        this.enrollments = enrollments;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
