package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.constants.CourseStatusEnum;

public class CourseResponseDTO {

    private Long id; // Unique identifier for the course
    private String title;
    private String description;
    private CourseStatusEnum status; // Status of the course
    private CoursePriceResponseDTO coursePrice; // Price details of the course
    private String thumbnail; // URL of the course thumbnail
    private CategoryEnum category;
    private UserResponseDTO instructor; // Instructor details
    private Long enrolledStudentsCount = 0L; // Number of students enrolled in the course


    public CourseResponseDTO(Long id, String title, String description, Long enrolledStudentsCount, CoursePriceResponseDTO coursePrice, UserResponseDTO instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.enrolledStudentsCount = enrolledStudentsCount != null ? enrolledStudentsCount : 0L; // Handle null values
        this.coursePrice = coursePrice;
        this.instructor = instructor;

    }

    public CourseResponseDTO(Long id, String title, String description, CoursePriceResponseDTO coursePrice, UserResponseDTO instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.coursePrice = coursePrice;
        this.instructor = instructor;
    }


    public CourseResponseDTO(Long id, String title, String description, CoursePriceResponseDTO coursePrice, CourseStatusEnum status, String thumbnail, CategoryEnum category, UserResponseDTO instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.coursePrice = coursePrice;
        this.thumbnail = thumbnail;
        this.category = category;
        this.instructor = instructor;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public UserResponseDTO getInstructor() {
        return instructor;
    }

    public void setInstructor(UserResponseDTO instructor) {
        this.instructor = instructor;
    }

    public Long getEnrolledStudentsCount() {
        return enrolledStudentsCount;
    }

    public void setEnrolledStudentsCount(Long enrolledStudentsCount) {
        this.enrolledStudentsCount = enrolledStudentsCount;
    }
}