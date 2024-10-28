package com.sarfaraz.elearning.rest.dto.inbound;

public class CourseModuleRequestDTO {
    private String title; // Heading of the module
    private String content; // Content of the module

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
