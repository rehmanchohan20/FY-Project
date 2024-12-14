package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Assignment;
import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.repository.AssignmentRepository;
import com.rehman.elearning.repository.CourseModuleRepository;
import com.rehman.elearning.rest.dto.inbound.AssignmentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.AssignmentResponseDTO;
import com.rehman.elearning.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final CourseModuleRepository courseModuleRepository;
    private final AssignmentRepository assignmentRepository;

    @Value("${assignment.upload.dir}")
    private String uploadDir;

    @Value("${assignment.server.url}")
    private String serverUrl;

    private static final Logger logger = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    public AssignmentServiceImpl(CourseModuleRepository courseModuleRepository, AssignmentRepository assignmentRepository) {
        this.courseModuleRepository = courseModuleRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public AssignmentResponseDTO uploadAssignment(Long moduleId, MultipartFile file, AssignmentRequestDTO requestDTO) throws IOException {
        logger.info("Uploading assignment for moduleId: {}", moduleId);

        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Save file locally
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String localFilePath = uploadDir + fileName;
        File destFile = new File(localFilePath);
        file.transferTo(destFile);

        // Map to server URL
        String fileUrl = serverUrl + fileName;

        // Create assignment
        Assignment assignment = new Assignment();
        assignment.setTitle(requestDTO.getHeading());
        assignment.setDescription(requestDTO.getDescription());
        assignment.setAssignmentPath(fileUrl);
        assignment.setCourseModule(module);
        assignment.setCreatedBy(UserCreatedBy.Self);

        // Save assignment
        assignment = assignmentRepository.save(assignment);

        return convertToResponseDTO(assignment);
    }

    @Override
    public AssignmentResponseDTO updateAssignment(Long assignmentId, MultipartFile file, AssignmentRequestDTO requestDTO) throws IOException {
        logger.info("Updating assignment with ID: {}", assignmentId);

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Save new file locally
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String localFilePath = uploadDir + fileName;
        File destFile = new File(localFilePath);
        file.transferTo(destFile);

        // Map to server URL
        String fileUrl = serverUrl + fileName;

        // Update assignment details
        assignment.setTitle(requestDTO.getHeading());
        assignment.setDescription(requestDTO.getDescription());
        assignment.setAssignmentPath(fileUrl);
        assignment.setCreatedBy(UserCreatedBy.Self);

        // Save updated assignment
        assignment = assignmentRepository.save(assignment);

        return convertToResponseDTO(assignment);
    }

    @Override
    public AssignmentResponseDTO getAssignment(Long assignmentId) {
        logger.info("Fetching assignment with ID: {}", assignmentId);

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        return convertToResponseDTO(assignment);
    }

    @Override
    public List<AssignmentResponseDTO> getAssignmentsByModule(Long moduleId) {
        logger.info("Fetching assignments for moduleId: {}", moduleId);

        List<Assignment> assignments = assignmentRepository.findByCourseModuleId(moduleId);
        return assignments.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteAssignment(Long assignmentId) {
        logger.info("Deleting assignment with ID: {}", assignmentId);

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        assignmentRepository.delete(assignment);
    }

    private AssignmentResponseDTO convertToResponseDTO(Assignment assignment) {
        AssignmentResponseDTO responseDTO = new AssignmentResponseDTO();
        responseDTO.setId(assignment.getId());
        responseDTO.setTitle(assignment.getTitle());
        responseDTO.setDescription(assignment.getDescription());
        responseDTO.setAssignmentPath(assignment.getAssignmentPath());
        return responseDTO;
    }
}
