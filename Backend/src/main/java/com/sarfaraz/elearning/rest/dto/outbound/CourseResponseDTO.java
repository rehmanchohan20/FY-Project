package com.sarfaraz.elearning.rest.dto.outbound;

import com.sarfaraz.elearning.model.Teacher;
import com.sarfaraz.elearning.model.User;

public class CourseResponseDTO {

    private Long id; // Unique identifier for the course
    private String title;
    private String description;
    private String status;
    private CoursePriceResponseDTO coursePrice; // Price details of the course


// Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CoursePriceResponseDTO getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePriceResponseDTO coursePrice) {
        this.coursePrice = coursePrice;
    }
}

