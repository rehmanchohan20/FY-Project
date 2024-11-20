package com.rehman.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_price")
public class CoursePrice  extends  Amount {

    @Id
    @Column(name = "course_id")
    private Long courseId;

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
