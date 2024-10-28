package com.sarfaraz.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_offer")
public class CourseOffer extends Amount {
    @Id
    @Column(name = "course_id")
    private Long courseId;

    private Double Discount;

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "course_id")
    private Course course;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
