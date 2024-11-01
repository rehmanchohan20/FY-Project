package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.CourseModuleLessonRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseModuleLessonResponseDTO;

import java.util.List;

public interface CourseModuleLessonService {
    List<CourseModuleLessonResponseDTO> addLesson(Long moduleId, List<CourseModuleLessonRequestDTO> requests);
    List<CourseModuleLessonResponseDTO> getAllLessons(Long moduleId);
    CourseModuleLessonResponseDTO updateLesson(Long lessonId, CourseModuleLessonRequestDTO request);
    void deleteLesson(Long lessonId);
}

