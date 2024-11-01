package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.MediaRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.MediaResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import com.sarfaraz.elearning.model.Media;

public interface MediaService {
    public MediaResponseDTO uploadVideo(MultipartFile videoFile, String url, String type, String duration);
    public MediaResponseDTO getMediaById(Long mediaId);
    public MediaResponseDTO updateMedia(Long mediaId, MediaRequestDTO request);
    public void deleteMedia(Long mediaId);

}
