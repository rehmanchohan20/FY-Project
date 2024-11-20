package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    public MediaResponseDTO uploadVideo(MultipartFile videoFile, String url, String type, String duration);
    public MediaResponseDTO getMediaById(Long mediaId);
    public MediaResponseDTO updateMedia(Long mediaId, MediaRequestDTO request);
    public void deleteMedia(Long mediaId);

}
