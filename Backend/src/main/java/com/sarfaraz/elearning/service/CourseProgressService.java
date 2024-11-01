package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.model.CourseProgress;
import com.sarfaraz.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import jakarta.transaction.Transactional;

public interface CourseProgressService {
    CourseProgressResponseDTO updateProgress(Long studentId, Long courseModuleLessonId, CourseProgressRequestDTO progressRequest) ;
    CourseProgressResponseDTO getProgress(Long studentId, Long courseModuleLessonId) ;
    CourseProgressResponseDTO markModuleAsCompleted(Long studentId, Long moduleId);
}
