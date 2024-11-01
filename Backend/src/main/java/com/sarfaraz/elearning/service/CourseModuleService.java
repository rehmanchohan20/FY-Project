package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseModuleResponseDTO;

import java.util.List;

public interface CourseModuleService {
    public List<CourseModuleResponseDTO> addModules(Long courseId, List<CourseModuleRequestDTO> requests);
    List<CourseModuleResponseDTO> getAllModules(Long courseId);
    CourseModuleResponseDTO updateModule(Long moduleId, CourseModuleRequestDTO request);
    void deleteModule(Long moduleId);
}

