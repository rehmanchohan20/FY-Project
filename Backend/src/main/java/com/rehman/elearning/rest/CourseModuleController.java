package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.rehman.elearning.rest.dto.inbound.MCQRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MCQResponseDTO;
import com.rehman.elearning.service.AssignmentService;
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

    @Autowired
    private AssignmentService assignmentService;

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


}
