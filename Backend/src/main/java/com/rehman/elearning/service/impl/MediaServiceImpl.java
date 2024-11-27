package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.CourseModuleLesson;
import com.rehman.elearning.model.Media;
import com.rehman.elearning.repository.CourseModuleLessonRepository;
import com.rehman.elearning.repository.MediaRepository;
import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private CourseModuleLessonRepository courseModuleLessonRepository;

    @Value("${course.media.server}")
    private String mediaServerUrl;

    @Value("${course.videos.upload}")
    private String mediaUploadDir;

    @Transactional
    @Override
    public MediaResponseDTO uploadVideo(String base64Video, Long courseId) throws IOException {
        if (base64Video == null || base64Video.length() == 0) {
            throw new IllegalArgumentException("No video data provided.");
        }

        if (base64Video.startsWith("data:video")) {
            base64Video = base64Video.split(",")[1];  // Clean base64 string
        }

        byte[] videoBytes = Base64.getDecoder().decode(base64Video);
        String videoFilePath = saveVideoToFile(videoBytes);  // Save video to file or cloud

        Media media = new Media();
        media.setUrl(videoFilePath);
        media.setType("video");

        Double videoDurationInSeconds = getVideoDuration(videoBytes);
        media.setDuration(String.valueOf(videoDurationInSeconds));

        Media savedMedia = mediaRepository.save(media);

        return convertToResponseDTO(savedMedia);
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
        dto.setDuration(formatDuration(Double.valueOf(media.getDuration())));  // Format duration
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

    // Dummy method to calculate video duration - you can replace it with actual logic
    private Double getVideoDuration(byte[] videoBytes) {
        // Use a library or external tool (e.g., FFmpeg) to extract duration from the video
        // For the purpose of this example, let's return a fixed duration of 300 seconds (5 minutes)
        return 300.0;
    }

    private String saveVideoToFile(byte[] videoBytes) throws IOException {
        // Create a directory if not exists
        File directory = new File(mediaUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique filename for the video
        String videoFileName = UUID.randomUUID().toString() + ".mp4";
        File videoFile = new File(directory, videoFileName);

        // Write video bytes to file
        try (FileOutputStream fileOutputStream = new FileOutputStream(videoFile)) {
            fileOutputStream.write(videoBytes);
        }

        // Return the video file path (local file path or URL)
        return mediaServerUrl + videoFile;
    }
}
