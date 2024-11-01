package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.sarfaraz.elearning.service.CourseModuleService;
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

    @PostMapping
    public ResponseEntity<List<CourseModuleResponseDTO>> createModules(
            @PathVariable Long courseId,
            @RequestBody List<CourseModuleRequestDTO> requests) {
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
    public ResponseEntity<Void> deleteModule( @PathVariable Long courseId, @PathVariable Long moduleId) {
        courseModuleService.deleteModule(moduleId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}

