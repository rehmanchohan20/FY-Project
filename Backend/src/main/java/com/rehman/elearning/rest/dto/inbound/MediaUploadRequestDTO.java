package com.rehman.elearning.rest.dto.inbound;

public class MediaUploadRequestDTO { // The ID of the course to link media
    private String media; // List of Base64-encoded media files

    // Getters and setters
    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}