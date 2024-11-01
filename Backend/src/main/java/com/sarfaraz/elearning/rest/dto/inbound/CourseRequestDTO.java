package com.sarfaraz.elearning.rest.dto.inbound;

import com.sarfaraz.elearning.model.Teacher;
import com.sarfaraz.elearning.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Course price must not be null")
    private CoursePriceRequestDTO coursePrice;

    private String status;

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

    public CoursePriceRequestDTO getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePriceRequestDTO coursePrice) {
        this.coursePrice = coursePrice;
    }
}
