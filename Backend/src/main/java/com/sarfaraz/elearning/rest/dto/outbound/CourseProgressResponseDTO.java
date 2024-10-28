package com.sarfaraz.elearning.rest.dto.outbound;

public class CourseProgressResponseDTO {
    private Long id;
    private CourseResponseDTO course;
    private String progressStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseResponseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseResponseDTO course) {
        this.course = course;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }
}
