package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.CourseModuleLesson;
import com.rehman.elearning.model.Media;
import com.rehman.elearning.repository.CourseModuleLessonRepository;
import com.rehman.elearning.repository.CourseModuleRepository;
import com.rehman.elearning.repository.MediaRepository;
import com.rehman.elearning.rest.dto.inbound.MediaRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private CourseModuleLessonRepository courseModuleLessonRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Value("${course.media.server}")
    private String mediaServerUrl;

    @Value("${course.videos.upload}")
    private String mediaUploadDir;

    @Value("${course.thumbnail.upload}")
    private String thumbnailUploadDir;

    @Value("${course.thumbnail.server}")
    private String thumbnailServerUrl;

    @Transactional
    @Override
    public MediaResponseDTO uploadVideo(MultipartFile videoFile) throws IOException {
        if (videoFile == null || videoFile.isEmpty()) {
            throw new IllegalArgumentException("No video file provided.");
        }
        // Save video to the file system and get the server URL
        String videoFileUrl = saveVideoToFile(videoFile);

        // Extract video duration
        Double videoDurationInSeconds;
        try {
            videoDurationInSeconds = getVideoDuration(new File(mediaUploadDir, videoFileUrl.substring(videoFileUrl.lastIndexOf("/") + 1)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            throw new IOException("Error while extracting video duration", e);
        }
        String formattedDuration = formatDuration(videoDurationInSeconds);

        // Create Media entity
        Media media = new Media();
        media.setTitle(videoFile.getOriginalFilename());
        media.setUrl(videoFileUrl);  // Save the server URL in the database
        media.setType("video");
        media.setDuration(formattedDuration);
        media.setCreatedBy(UserCreatedBy.Teacher);

        // Save media entity
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
        media.setTitle(request.getTitle());
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
        dto.setTitle(media.getTitle());
        dto.setId(media.getId());
        dto.setUrl(media.getUrl());
        dto.setType(media.getType());
        dto.setDuration(media.getDuration());  // Duration already formatted
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

    private Double getVideoDuration(File videoFile) throws IOException, InterruptedException {
        // Create a temporary file to store the video
        File tempFile = new File(mediaUploadDir, UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(videoFile.getName()));

        // Use FileInputStream to read the video file into a byte array and write it to the temporary file
        try (FileInputStream fileInputStream = new FileInputStream(videoFile);
             FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Use FFmpeg to get video duration without creating an output file
        ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", tempFile.getAbsolutePath(), "-f", "null", "-");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String output = new String(process.getInputStream().readAllBytes());
        process.waitFor();

        // Extract the duration from FFmpeg output
        String durationString = extractDurationFromOutput(output);
        if (durationString.isEmpty()) {
            throw new IOException("Duration string is empty. FFmpeg output might be malformed.");
        }

        // Convert the duration string to seconds
        double totalSeconds = convertDurationToSeconds(durationString);

        tempFile.delete(); // Delete the temporary file after use

        return totalSeconds;
    }

    private String extractDurationFromOutput(String ffmpegOutput) {
        // Search for 'time=' in the output and extract the value
        Pattern pattern = Pattern.compile("time=(\\d{2}:\\d{2}:\\d{2}\\.\\d{2})");
        Matcher matcher = pattern.matcher(ffmpegOutput);
        if (matcher.find()) {
            return matcher.group(1); // Return the matched time
        }

        // If 'time=' is not found, return an empty string
        return "";
    }

    private double convertDurationToSeconds(String durationString) {
        // Ensure durationString format is as expected (HH:MM:SS.sss)
        String[] timeParts = durationString.split(":");
        double hours = Double.parseDouble(timeParts[0]);
        double minutes = Double.parseDouble(timeParts[1]);
        String[] secondsParts = timeParts[2].split("\\.");
        double seconds = Double.parseDouble(secondsParts[0]);
        double fractionalSeconds = (secondsParts.length > 1) ? Double.parseDouble(secondsParts[1]) / Math.pow(10, secondsParts[1].length()) : 0;

        return hours * 3600 + minutes * 60 + seconds + fractionalSeconds;
    }
    private String saveVideoToFile(MultipartFile videoFile) throws IOException {
        // Create the upload directory if it doesn't exist
        File directory = new File(mediaUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // Ensure directory is created if it does not exist
        }

        // Generate a unique file name
        String videoFileName = UUID.randomUUID().toString() + ".mp4";
        File videoFilePath = new File(directory, videoFileName);

        // Save video file to disk
        try (FileOutputStream fileOutputStream = new FileOutputStream(videoFilePath)) {
            fileOutputStream.write(videoFile.getBytes());
        }

        // Construct the server URL for the uploaded file
        String fileUrl = (mediaServerUrl.endsWith("/") ? mediaServerUrl : mediaServerUrl + "/") + videoFileName;
        fileUrl = fileUrl.replace("\\", "/");  // Ensure forward slashes in URL

        // Log the server URL for debugging
        System.out.println("Generated server URL: " + fileUrl);

        return fileUrl;  // Return the server URL
    }

    @Override
    public MediaResponseDTO uploadThumbnail(MultipartFile thumbnailFile) throws IOException {
        if (thumbnailFile == null || thumbnailFile.isEmpty()) {
            throw new IllegalArgumentException("No thumbnail file provided.");
        }

        // Save thumbnail to the file system and get the server URL
        String thumbnailFileUrl = saveThumbnailToFile(thumbnailFile);

        // Create Media entity for the thumbnail
        Media media = new Media();
        media.setTitle(thumbnailFile.getOriginalFilename());
        media.setUrl(thumbnailFileUrl);  // Save the server URL in the database
        media.setType("thumbnail");
        media.setCreatedBy(UserCreatedBy.Teacher);

        // Save media entity
        Media savedMedia = mediaRepository.save(media);
        return convertToResponseDTO(savedMedia);
    }

    private String saveThumbnailToFile(MultipartFile thumbnailFile) throws IOException {
        // Save the file just like the video method but with the thumbnail directory and extension
        File directory = new File(thumbnailUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // Ensure directory is created if it does not exist
        }

        String thumbnailFileName = UUID.randomUUID().toString() + ".jpg";  // Or use another extension based on file type
        File thumbnailFilePath = new File(directory, thumbnailFileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(thumbnailFilePath)) {
            fileOutputStream.write(thumbnailFile.getBytes());
        }

        // Construct the server URL for the uploaded thumbnail
        String fileUrl = (thumbnailServerUrl.endsWith("/") ? thumbnailServerUrl : thumbnailServerUrl + "/") + thumbnailFileName;
        fileUrl = fileUrl.replace("\\", "/");  // Ensure forward slashes in URL

        return fileUrl;
    }



}