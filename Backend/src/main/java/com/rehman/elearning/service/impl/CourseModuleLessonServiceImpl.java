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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(CourseModuleLessonServiceImpl.class);

    @Override
    @Transactional
    public List<CourseModuleLessonResponseDTO> addLesson(Long moduleId, List<CourseModuleLessonRequestDTO> requests) {
        log.info("Adding lessons to module with ID: {}", moduleId);

        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        List<CourseModuleLessonResponseDTO> responseList = new ArrayList<>();
        for (CourseModuleLessonRequestDTO request : requests) {
            CourseModuleLesson lesson = createLessonFromRequest(request);
            lesson.setCourseModule(module);
            lesson = lessonRepository.save(lesson);
            responseList.add(convertToResponseDTO(lesson));
        }

        log.info("Successfully added {} lessons to module with ID: {}", responseList.size(), moduleId);
        return responseList;
    }

    @Override
    @Transactional
    public List<CourseModuleLessonResponseDTO> getAllLessons(Long moduleId) {
        log.info("Fetching all lessons for module with ID: {}", moduleId);

        List<CourseModuleLesson> lessons = lessonRepository.findByCourseModule_Id(moduleId);
        if (lessons.isEmpty()) {
            log.warn("No lessons found for module with ID: {}", moduleId);
        }

        return lessons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseModuleLessonResponseDTO updateLesson(Long lessonId, CourseModuleLessonRequestDTO request) {
        log.info("Updating lesson with ID: {}", lessonId);

        CourseModuleLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND));

        updateLessonFromRequest(lesson, request);
        lesson = lessonRepository.save(lesson);

        log.info("Successfully updated lesson with ID: {}", lessonId);
        return convertToResponseDTO(lesson);
    }

    @Override
    @Transactional
    public void deleteLesson(Long lessonId) {
        log.info("Deleting lesson with ID: {}", lessonId);
        if (!lessonRepository.existsById(lessonId)) {
            throw new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND);
        }
        lessonRepository.deleteById(lessonId);
        log.info("Successfully deleted lesson with ID: {}", lessonId);
    }

    @Override
    @Transactional
    public List<MediaResponseDTO> getMediaByLessonId(Long lessonId) {
        log.info("Fetching media for lesson with ID: {}", lessonId);

        CourseModuleLesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND));

        return lesson.getMedias().stream()
                .map(this::convertMediaToDTO)
                .collect(Collectors.toList());
    }

    // --- Private Helper Methods ---

    private CourseModuleLesson createLessonFromRequest(CourseModuleLessonRequestDTO request) {
        CourseModuleLesson lesson = new CourseModuleLesson();
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setCreatedBy(UserCreatedBy.Teacher);
        lesson.setFeaturedLesson(request.getFeaturedLesson());
        lesson.setPriority(request.getPriority());
        lesson.setContentLock(request.getIsContentLock());
        lesson.setStatus(request.getStatus());

        if (request.getMediaIds() != null && !request.getMediaIds().isEmpty()) {
            Set<Media> medias = fetchAndValidateMedia(request.getMediaIds());
            lesson.setMedias(medias);
            lesson.setDuration(calculateTotalDuration(medias));
        }

        return lesson;
    }

    private void updateLessonFromRequest(CourseModuleLesson lesson, CourseModuleLessonRequestDTO request) {
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());
        lesson.setFeaturedLesson(request.getFeaturedLesson());
        lesson.setPriority(request.getPriority());
        lesson.setContentLock(request.getIsContentLock());
        lesson.setStatus(request.getStatus());

        if (request.getMediaIds() != null && !request.getMediaIds().isEmpty()) {
            Set<Media> medias = fetchAndValidateMedia(request.getMediaIds());
            lesson.setMedias(medias);
            lesson.setDuration(calculateTotalDuration(medias));
        }
    }

    private Set<Media> fetchAndValidateMedia(Set<Long> mediaIds) {
        return mediaIds.stream()
                .map(mediaId -> mediaRepository.findById(mediaId)
                        .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.MEDIA_NOT_FOUND)))
                .collect(Collectors.toSet());
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
            log.error("Invalid duration format: {}", duration, e);
            return 0;
        }
    }

    private String convertSecondsToDurationFormat(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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

        if (lesson.getMedias() != null) {
            dto.setMediaDetails(
                    lesson.getMedias().stream()
                            .map(this::convertMediaToDTO)
                            .collect(Collectors.toSet())
            );
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
}
