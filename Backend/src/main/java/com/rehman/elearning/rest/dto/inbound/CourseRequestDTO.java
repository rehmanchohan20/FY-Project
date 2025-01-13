package com.rehman.elearning.rest.dto.inbound;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.constants.CourseStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Course price must not be null")
    private CoursePriceRequestDTO coursePrice;

    private CourseStatusEnum status;

    @NotNull(message = "Category should not be null")
    private CategoryEnum category;

    public CourseRequestDTO() {
    }

    public CourseRequestDTO(String title, String description, CoursePriceRequestDTO coursePrice, CourseStatusEnum status, CategoryEnum category) {
        this.title = title;
        this.description = description;
        this.coursePrice = coursePrice;
        this.status = status;
        this.category = category;
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

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public CoursePriceRequestDTO getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePriceRequestDTO coursePrice) {
        this.coursePrice = coursePrice;
    }
}
