package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseProgressResponseDTO;

public interface CourseProgressService {
    CourseProgressResponseDTO updateProgress(Long studentId, Long courseModuleLessonId, CourseProgressRequestDTO progressRequest) ;
    CourseProgressResponseDTO getProgress(Long studentId, Long courseModuleLessonId) ;
    CourseProgressResponseDTO markModuleAsCompleted(Long studentId, Long moduleId);
}
