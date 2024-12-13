package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.rehman.elearning.rest.dto.inbound.MCQRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MCQResponseDTO;
import com.rehman.elearning.service.CourseModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/courses/{courseId}/modules")
public class CourseModuleController {

    @Autowired
    private CourseModuleService courseModuleService;

    // Module CRUD operations
    @PostMapping
    public ResponseEntity<List<CourseModuleResponseDTO>> createModules(
            @PathVariable Long courseId,
            @Valid @RequestBody List<CourseModuleRequestDTO> requests) {
        List<CourseModuleResponseDTO> modules = courseModuleService.addModules(courseId, requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(modules);
    }

    @GetMapping
    public ResponseEntity<List<CourseModuleResponseDTO>> getAllModules(@PathVariable Long courseId) {
        List<CourseModuleResponseDTO> response = courseModuleService.getAllModules(courseId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{moduleId}")
    public ResponseEntity<CourseModuleResponseDTO> updateModule(
            @PathVariable Long courseId, @PathVariable Long moduleId, @RequestBody CourseModuleRequestDTO request) {
        CourseModuleResponseDTO response = courseModuleService.updateModule(moduleId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long courseId, @PathVariable Long moduleId) {
        courseModuleService.deleteModule(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Assignment CRUD operations
    @PostMapping("/{moduleId}/assignments")
    public ResponseEntity<String> uploadAssignment(@PathVariable Long moduleId, @RequestParam("file") MultipartFile file) throws Exception {
        String fileUrl = courseModuleService.uploadAssignment(moduleId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(fileUrl);
    }

    @GetMapping("/{moduleId}/assignments")
    public ResponseEntity<String> getAssignment(@PathVariable Long moduleId) {
        String assignmentPath = courseModuleService.getAssignment(moduleId);
        return ResponseEntity.ok(assignmentPath);
    }

    @PutMapping("/{moduleId}/assignments")
    public ResponseEntity<String> updateAssignment(
            @PathVariable Long moduleId, @RequestParam("file") MultipartFile file) throws Exception {
        String fileUrl = courseModuleService.updateAssignment(moduleId, file);
        return ResponseEntity.ok(fileUrl);
    }

    @DeleteMapping("/{moduleId}/assignments")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long moduleId) {
        courseModuleService.deleteAssignment(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // MCQ CRUD operations
    @PostMapping("/{moduleId}/mcqs")
    public ResponseEntity<Void> addMCQsToModule(
            @PathVariable Long moduleId, @Valid @RequestBody List<MCQRequestDTO> mcqRequestDTOs) {
        courseModuleService.saveMCQs(moduleId, mcqRequestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{moduleId}/mcqs")
    public ResponseEntity<List<MCQResponseDTO>> getMCQs(@PathVariable Long moduleId) {
        List<MCQResponseDTO> mcqs = courseModuleService.getMCQsForModule(moduleId);
        return ResponseEntity.ok(mcqs);
    }

    @PutMapping("/{moduleId}/mcqs/{mcqId}")
    public ResponseEntity<MCQResponseDTO> updateMCQ(
            @PathVariable Long courseId, @PathVariable Long moduleId, @PathVariable Long mcqId, @RequestBody MCQRequestDTO request) {
        MCQResponseDTO updatedMCQ = courseModuleService.updateMCQ(moduleId, mcqId, request);
        return ResponseEntity.ok(updatedMCQ);
    }

    @DeleteMapping("/{moduleId}/mcqs/{mcqId}")
    public ResponseEntity<Void> deleteMCQ(@PathVariable Long moduleId, @PathVariable Long mcqId) {
        courseModuleService.deleteMCQ(moduleId, mcqId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
