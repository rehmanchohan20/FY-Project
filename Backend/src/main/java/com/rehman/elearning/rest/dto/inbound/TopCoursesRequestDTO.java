package com.rehman.elearning.rest.dto.inbound;

public class TopCoursesRequestDTO {
    // Add any fields you may need for the request
    private String category;
    private String status;

    // Getters and setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}