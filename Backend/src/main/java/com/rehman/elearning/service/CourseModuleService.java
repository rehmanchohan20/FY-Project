package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.rehman.elearning.rest.dto.inbound.MCQRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MCQResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseModuleService {

    List<CourseModuleResponseDTO> addModules(Long courseId, List<CourseModuleRequestDTO> requests);
    List<CourseModuleResponseDTO> getAllModules(Long courseId);
    CourseModuleResponseDTO updateModule(Long moduleId, CourseModuleRequestDTO request);
    void deleteModule(Long moduleId);


    // Save MCQs
    void saveMCQs(Long moduleId, List<MCQRequestDTO> mcqRequestDTOs);
    public MCQResponseDTO updateMCQ(Long moduleId, Long mcqId, MCQRequestDTO request);
    public List<MCQResponseDTO> getMCQsForModule(Long moduleId);
    public void deleteMCQ(Long moduleId, Long mcqId);
}
