package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.model.Course;

import java.util.List;

public class TopCoursesResponseDTO {

    private List<Course> topCourses;  // List of top courses based on enrollments
    private Long totalEnrollments;    // Total enrollments for the top courses

    // Getters and setters
    public List<Course> getTopCourses() {
        return topCourses;
    }

    public void setTopCourses(List<Course> topCourses) {
        this.topCourses = topCourses;
    }

    public Long getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(Long totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }
}
