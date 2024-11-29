package com.rehman.elearning.rest.dto.inbound;

import org.springframework.web.multipart.MultipartFile;

public class MediaUploadRequestDTO {
    private MultipartFile media; // Multipart file for media upload

    // Getters and setters
    public MultipartFile getMedia() {
        return media;
    }

    public void setMedia(MultipartFile media) {
        this.media = media;
    }
}
