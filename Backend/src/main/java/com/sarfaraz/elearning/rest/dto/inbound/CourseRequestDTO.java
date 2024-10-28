package com.sarfaraz.elearning.rest.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class CourseRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String status; // Optional

    @NotNull(message = "Teacher ID must not be null")
    private Long teacherId;

    private CoursePriceRequestDTO coursePrice;
    private CourseOfferRequestDTO courseOffer;
    private Set<CourseModuleRequestDTO> courseModules; // Set of modules related to the course

    // Getters and Setters

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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public CoursePriceRequestDTO getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePriceRequestDTO coursePrice) {
        this.coursePrice = coursePrice;
    }

    public CourseOfferRequestDTO getCourseOffer() {
        return courseOffer;
    }

    public void setCourseOffer(CourseOfferRequestDTO courseOffer) {
        this.courseOffer = courseOffer;
    }

    public Set<CourseModuleRequestDTO> getCourseModules() {
        return courseModules;
    }

    public void setCourseModules(Set<CourseModuleRequestDTO> courseModules) {
        this.courseModules = courseModules;
    }
}
