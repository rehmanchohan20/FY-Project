package com.rehman.elearning.service;


import com.rehman.elearning.rest.dto.outbound.RecommendedCourseResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRecommendationService{
    List<RecommendedCourseResponseDTO> getRecommendedCourses(Long userId);
}
