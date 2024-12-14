package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.AssignmentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.AssignmentResponseDTO;
import com.rehman.elearning.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    private final AssignmentService assignmentService;

    @Autowired
    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/upload/{moduleId}")
    public ResponseEntity<AssignmentResponseDTO> uploadAssignment(
            @PathVariable Long moduleId,
            @RequestParam("file") MultipartFile file,
            @ModelAttribute AssignmentRequestDTO requestDTO) {
        try {
            AssignmentResponseDTO responseDTO = assignmentService.uploadAssignment(moduleId, file, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IOException e) {
            logger.error("Error uploading assignment", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update/{assignmentId}")
    public ResponseEntity<AssignmentResponseDTO> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file,
            @ModelAttribute AssignmentRequestDTO requestDTO) {
        try {
            AssignmentResponseDTO responseDTO = assignmentService.updateAssignment(assignmentId, file, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IOException e) {
            logger.error("Error updating assignment", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponseDTO> getAssignment(@PathVariable Long assignmentId) {
        AssignmentResponseDTO responseDTO = assignmentService.getAssignment(assignmentId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<List<AssignmentResponseDTO>> getAssignmentsByModule(@PathVariable Long moduleId) {
        List<AssignmentResponseDTO> assignments = assignmentService.getAssignmentsByModule(moduleId);
        return ResponseEntity.ok(assignments);
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.noContent().build();
    }
}
