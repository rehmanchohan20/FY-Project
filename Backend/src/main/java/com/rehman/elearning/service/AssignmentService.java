package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.AssignmentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.AssignmentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AssignmentService {
    public AssignmentResponseDTO uploadAssignment(Long moduleId, MultipartFile file, AssignmentRequestDTO requestDTO) throws IOException;
    public AssignmentResponseDTO updateAssignment(Long assignmentId, MultipartFile file, AssignmentRequestDTO requestDTO) throws IOException;
    public AssignmentResponseDTO getAssignment(Long assignmentId);
    public List<AssignmentResponseDTO> getAssignmentsByModule(Long moduleId);
    public void deleteAssignment(Long assignmentId);



}
