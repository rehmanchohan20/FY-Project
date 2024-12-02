package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.inbound.MediaUploadRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {

    // Method for uploading video (changed to MultipartFile)
    public MediaResponseDTO uploadVideo(MultipartFile videoFile) throws IOException;

    // Method to get media by ID
    MediaResponseDTO getMediaById(Long mediaId);

    // Method to update media
    MediaResponseDTO updateMedia(Long mediaId, MediaRequestDTO request);

    public MediaResponseDTO uploadThumbnail(MultipartFile thumbnailFile) throws IOException;

    // Method to delete media
    void deleteMedia(Long mediaId);
}
