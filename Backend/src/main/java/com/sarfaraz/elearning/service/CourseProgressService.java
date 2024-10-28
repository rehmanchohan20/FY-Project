package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseProgressResponseDTO;

import java.util.List;

public interface CourseProgressService {
    CourseProgressResponseDTO createCourseProgress(CourseProgressRequestDTO courseProgressRequestDto);
    CourseProgressResponseDTO updateCourseProgress(Long id, CourseProgressRequestDTO courseProgressRequestDto);
    CourseProgressResponseDTO getCourseProgressById(Long id);
    List<CourseProgressResponseDTO> getAllCourseProgress();
    void deleteCourseProgress(Long id);
}
