package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import com.sarfaraz.elearning.model.CourseProgress;
import com.sarfaraz.elearning.repository.CourseProgressRepository;
import com.sarfaraz.elearning.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseProgressServiceImpl implements CourseProgressService {

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Override
    public CourseProgressResponseDTO createCourseProgress(CourseProgressRequestDTO courseProgressRequestDto) {
        CourseProgress courseProgress = new CourseProgress();
        // Set properties from DTO to the entity
        return mapToResponse(courseProgressRepository.save(courseProgress));
    }

    @Override
    public CourseProgressResponseDTO updateCourseProgress(Long id, CourseProgressRequestDTO courseProgressRequestDto) {
        CourseProgress courseProgress = courseProgressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseProgress not found"));
        // Update properties from DTO to the entity
        return mapToResponse(courseProgressRepository.save(courseProgress));
    }

    @Override
    public CourseProgressResponseDTO getCourseProgressById(Long id) {
        CourseProgress courseProgress = courseProgressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CourseProgress not found"));
        return mapToResponse(courseProgress);
    }

    @Override
    public List<CourseProgressResponseDTO> getAllCourseProgress() {
        return courseProgressRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCourseProgress(Long id) {
        courseProgressRepository.deleteById(id);
    }

    private CourseProgressResponseDTO mapToResponse(CourseProgress courseProgress) {
        CourseProgressResponseDTO dto = new CourseProgressResponseDTO();
        // Map properties from entity to DTO
        return dto;
    }
}
