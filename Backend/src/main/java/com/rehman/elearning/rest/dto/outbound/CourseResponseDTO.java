package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.constants.CourseStatusEnum;

public class CourseResponseDTO {

    private Long id; // Unique identifier for the course
    private String title;
    private String description;
    private CourseStatusEnum status; // Status of the course
    private CoursePriceResponseDTO coursePrice; // Price details of the course

    // Constructor accepting CoursePriceResponseDTO
    public CourseResponseDTO(Long id, String title, String description, CoursePriceResponseDTO coursePrice, CourseStatusEnum status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.coursePrice = coursePrice;

    }

    // No-argument constructor
    public CourseResponseDTO() {
    }

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

    public CourseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CourseStatusEnum status) {
        this.status = status;
    }

    public CoursePriceResponseDTO getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePriceResponseDTO coursePrice) {
        this.coursePrice = coursePrice;
    }
}
