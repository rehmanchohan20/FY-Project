package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseProgressResponseDTO;

import java.util.List;

public interface CourseProgressService {
    CourseProgressResponseDTO updateProgress(Long studentId, Long courseModuleLessonId, CourseProgressRequestDTO progressRequest);
    CourseProgressResponseDTO getProgress(Long studentId, Long courseModuleLessonId);
    CourseProgressResponseDTO markModuleAsCompleted(Long studentId, Long moduleId);
    CourseProgressResponseDTO getOverallCourseProgress(Long userId, Long courseId);
    List<CourseProgressResponseDTO> getAllCoursesProgress(Long userId) ;
}