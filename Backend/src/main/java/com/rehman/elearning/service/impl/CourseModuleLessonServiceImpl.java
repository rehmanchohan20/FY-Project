package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.model.CourseModuleLesson;
import com.rehman.elearning.model.Media;
import com.rehman.elearning.repository.CourseModuleLessonRepository;
import com.rehman.elearning.repository.CourseModuleRepository;
import com.rehman.elearning.repository.MediaRepository;
import com.rehman.elearning.rest.dto.inbound.CourseModuleLessonRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleLessonResponseDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.CourseModuleLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseModuleLessonServiceImpl implements CourseModuleLessonService {

    @Autowired
    private CourseModuleLessonRepository lessonRepository;

    @Autowired
    private CourseModuleRepository moduleRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Override
    @Transactional
    public List<CourseModuleLessonResponseDTO> addLesson(Long moduleId, List<CourseModuleLessonRequestDTO> requests) {
        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        List<CourseModuleLessonResponseDTO> responseList = new ArrayList<>();

        for (CourseModuleLessonRequestDTO request : requests) {
            CourseModuleLesson lesson = new CourseModuleLesson();
            lesson.setTitle(request.getTitle());
            lesson.setDescription(request.getDescription());
            lesson.setCreatedBy(UserCreatedBy.Teacher);
            lesson.setFeaturedLesson(request.getFeaturedLesson());
            lesson.setPriority(request.getPriority());
            lesson.setContentLock(request.getIsContentLock());
            lesson.setStatus(request.getStatus());

            // Handle media relationships with validation
            if (request.getMediaIds() != null && !request.getMediaIds().isEmpty()) {
                Set<Media> medias = request.getMediaIds().stream()
                        .map(mediaId -> mediaRepository.findById(mediaId)
                                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.MEDIA_NOT_FOUND)))
                        .collect(Collectors.toSet());
                lesson.setMedias(medias);

                // Calculate the total duration from the media
                String totalDuration = calculateTotalDuration(medias);
                lesson.setDuration(totalDuration);
            }

            lesson.setCourseModule(module);
            lesson = lessonRepository.save(lesson);
            responseList.add(convertToResponseDTO(lesson));
        }

        return responseList;
    }


    @Override
    public List<CourseModuleLessonResponseDTO> getAllLessons(Long moduleId) {
        List<CourseModuleLesson> lessons = lessonRepository.findByModuleId(moduleId);
        return lessons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseModuleLessonResponseDTO updateLesson(Long lessonId, CourseModuleLessonRequestDTO request) {
        CourseModuleLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setFeaturedLesson(request.getFeaturedLesson());
        lesson.setCreatedBy(UserCreatedBy.Teacher);
        lesson.setPriority(request.getPriority());
        lesson.setContentLock(request.getIsContentLock());
        lesson.setStatus(request.getStatus());

        // Handle media relationships
        if (request.getMediaIds() != null && !request.getMediaIds().isEmpty()) {
            Set<Media> medias = request.getMediaIds().stream()
                    .map(mediaId -> mediaRepository.findById(mediaId)
                            .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.MEDIA_NOT_FOUND)))
                    .collect(Collectors.toSet());
            lesson.setMedias(medias);

            // Calculate the total duration from the media
            String totalDuration = calculateTotalDuration(medias);
            lesson.setDuration(totalDuration);
        } else {
            lesson.setMedias(null);
            lesson.setDuration("00:00:00"); // Set duration to zero if no media is associated
        }

        lesson = lessonRepository.save(lesson);
        return convertToResponseDTO(lesson);
    }


    @Override
    public void deleteLesson(Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    private CourseModuleLessonResponseDTO convertToResponseDTO(CourseModuleLesson lesson) {
        CourseModuleLessonResponseDTO dto = new CourseModuleLessonResponseDTO();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getDescription());
        dto.setFeaturedLesson(lesson.getFeaturedLesson());
        dto.setStatus(lesson.getStatus());
        dto.setIsContentLock(lesson.getContentLock());
        dto.setPriority(lesson.getPriority());
        dto.setDuration(lesson.getDuration());

        // Populate mediaDetails
        if (lesson.getMedias() != null) {
            Set<MediaResponseDTO> mediaDetails = lesson.getMedias().stream()
                    .map(this::convertMediaToDTO)
                    .collect(Collectors.toSet());
            dto.setMediaDetails(mediaDetails);
        }

        return dto;
    }

    private MediaResponseDTO convertMediaToDTO(Media media) {
        MediaResponseDTO dto = new MediaResponseDTO();
        dto.setId(media.getId());
        dto.setUrl(media.getUrl());
        dto.setType(media.getType());
        dto.setDuration(media.getDuration());
        return dto;
    }


    private String calculateTotalDuration(Set<Media> medias) {
        int totalSeconds = medias.stream()
                .mapToInt(media -> parseDurationToSeconds(media.getDuration()))
                .sum();
        return convertSecondsToDurationFormat(totalSeconds);
    }

    private int parseDurationToSeconds(String duration) {
        try {
            String[] parts = duration.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            return hours * 3600 + minutes * 60 + seconds;
        } catch (Exception e) {
            // Log or handle invalid duration formats
            return 0;
        }
    }

    private String convertSecondsToDurationFormat(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
