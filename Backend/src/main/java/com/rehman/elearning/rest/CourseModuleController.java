package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.rehman.elearning.service.AssignmentService;
import com.rehman.elearning.service.CourseModuleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/modules")
public class CourseModuleController {

    @Autowired
    private CourseModuleService courseModuleService;

    @Autowired
    private AssignmentService assignmentService;
    private static final Logger logger = LoggerFactory.getLogger(CourseModuleController.class);

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
//
//    @PutMapping("/updateModules")
//    public ResponseEntity<List<CourseModuleResponseDTO>> updateModules(
//            @PathVariable Long moduleId,
//            @RequestBody List<CourseModuleRequestDTO> requests) {
//        List<CourseModuleResponseDTO> response = courseModuleService.updateModules(moduleId ,requests);
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/updateModules/{moduleId}")
    public ResponseEntity<List<CourseModuleResponseDTO>> updateModules(
            @PathVariable Long moduleId,
            @RequestBody List<CourseModuleRequestDTO> requests) {
        List<CourseModuleResponseDTO> response = courseModuleService.updateModules(moduleId, requests);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{moduleId}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long moduleId) {
        courseModuleService.deleteModule(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
