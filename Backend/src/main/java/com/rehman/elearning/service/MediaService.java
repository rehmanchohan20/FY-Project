package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;

import java.io.IOException;

public interface MediaService {

    // Method for uploading video
    public MediaResponseDTO uploadVideo(String base64Video, Long courseId) throws IOException ;

    // Method to get media by ID
    MediaResponseDTO getMediaById(Long mediaId);

    // Method to update media
    MediaResponseDTO updateMedia(Long mediaId, MediaRequestDTO request);

    // Method to delete media
    void deleteMedia(Long mediaId);
}
