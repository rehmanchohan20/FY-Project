package com.rehman.elearning.rest.dto.outbound;

import java.util.Set;

public class CourseModuleLessonResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String duration;
    private Integer priority;
    private String featuredLesson; // Optional field
    private Boolean isContentLock; // Optional field
    private String status;

    // Replace mediaIds with detailed media information
    private Set<MediaResponseDTO> mediaDetails;

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

    public Set<MediaResponseDTO> getMediaDetails() {
        return mediaDetails;
    }

    public void setMediaDetails(Set<MediaResponseDTO> mediaDetails) {
        this.mediaDetails = mediaDetails;
    }
}
