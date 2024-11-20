package com.rehman.elearning.rest.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public class CourseModuleLessonRequestDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String duration;

    @NotNull(message = "Priority is required")
    private Integer priority; // Priority field (1, 2, 3, etc.)

    private String featuredLesson;

    private Boolean isContentLock;

    private String status;

    private Set<Long> mediaIds;



    // Getters and setters

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getFeaturedLesson() {
        return featuredLesson;
    }

    public void setFeaturedLesson(String featuredLesson) {
        this.featuredLesson = featuredLesson;
    }

    public Boolean getIsContentLock() {
        return isContentLock;
    }

    public void setIsContentLock(Boolean isContentLock) {
        this.isContentLock = isContentLock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Long> getMediaIds() {
        return mediaIds;
    }

    public void setMediaIds(Set<Long> mediaIds) {
        this.mediaIds = mediaIds;
    }
}
