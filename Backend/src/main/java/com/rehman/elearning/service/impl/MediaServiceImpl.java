package com.rehman.elearning.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Media;
import com.rehman.elearning.repository.CourseModuleLessonRepository;
import com.rehman.elearning.repository.MediaRepository;
import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class MediaServiceImpl implements MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);

    private final Cloudinary cloudinary;
    private final MediaRepository mediaRepository;
    private final CourseModuleLessonRepository courseModuleLessonRepository;

    @Autowired
    public MediaServiceImpl(Cloudinary cloudinary, MediaRepository mediaRepository,CourseModuleLessonRepository courseModuleLessonRepository ) {
        this.cloudinary = cloudinary;
        this.mediaRepository = mediaRepository;
        this.courseModuleLessonRepository = courseModuleLessonRepository;
    }

    @Override
    public MediaResponseDTO uploadVideo(MultipartFile videoFile, String url, String type, String duration) {
        // Validate file type and size if needed
        try {
            // Upload the video to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(videoFile.getBytes(),
                    ObjectUtils.asMap("resource_type", "video"));

            // Extract the URL and other details from the result
            String videoUrl = (String) uploadResult.get("secure_url");
            String format = (String) uploadResult.get("format");
            String videoType = "video/" + format;

            // Extract duration in seconds and convert it to HH:mm:ss format
            Double durationInSeconds = (Double) uploadResult.get("duration");
            String formattedDuration = formatDuration(durationInSeconds);

            // Create a new Media entity and save it to the database
            Media media = new Media();
            media.setUrl(videoUrl);
            media.setType(videoType);
            media.setDuration(formattedDuration);
            media.setCreatedBy(UserCreatedBy.Teacher);

            Media savedMedia = mediaRepository.save(media);

            return convertToResponseDTO(savedMedia); // Convert entity to DTO before returning
        } catch (IOException e) {
            logger.error("Failed to upload video", e);
            throw new RuntimeException("Failed to upload video", e);
        }
    }

    @Override
    public MediaResponseDTO getMediaById(Long mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        return convertToResponseDTO(media);
    }

    @Override
    public MediaResponseDTO updateMedia(Long mediaId, MediaRequestDTO request) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Update media properties
        media.setUrl(request.getUrl());
        media.setType(request.getType());
        media.setDuration(request.getDuration());
        media.setCreatedBy(UserCreatedBy.Teacher);

        Media updatedMedia = mediaRepository.save(media);
        return convertToResponseDTO(updatedMedia);
    }

    @Override
    public void deleteMedia(Long mediaId) {
        courseModuleLessonRepository.deleteById(mediaId);
    }

    private MediaResponseDTO convertToResponseDTO(Media media) {
        MediaResponseDTO dto = new MediaResponseDTO();
        dto.setId(media.getId());
        dto.setUrl(media.getUrl());
        dto.setType(media.getType());
        dto.setDuration(media.getDuration());
        return dto;
    }

    // Utility to format duration from seconds to HH:mm:ss
    private String formatDuration(Double durationInSeconds) {
        if (durationInSeconds == null) return null;
        int hours = (int) (durationInSeconds / 3600);
        int minutes = (int) ((durationInSeconds % 3600) / 60);
        int seconds = (int) (durationInSeconds % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
