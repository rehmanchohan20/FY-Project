package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CourseModuleLessonRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleLessonResponseDTO;

import java.util.List;

public interface CourseModuleLessonService {
    List<CourseModuleLessonResponseDTO> addLesson(Long moduleId, List<CourseModuleLessonRequestDTO> requests);
    List<CourseModuleLessonResponseDTO> getAllLessons(Long moduleId);
    CourseModuleLessonResponseDTO updateLesson(Long lessonId, CourseModuleLessonRequestDTO request);
    void deleteLesson(Long lessonId);
}

