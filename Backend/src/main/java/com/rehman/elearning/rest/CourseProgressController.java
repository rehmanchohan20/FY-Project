package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import com.rehman.elearning.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course-progress")
public class CourseProgressController {

    @Autowired
    private CourseProgressService courseProgressService;

    @PostMapping("/{studentId}/{courseModuleLessonId}/update")
    public ResponseEntity<CourseProgressResponseDTO> updateProgress(
            @PathVariable Long studentId,
            @PathVariable Long courseModuleLessonId,
            @RequestBody CourseProgressRequestDTO progressRequest) {

        CourseProgressResponseDTO updatedProgress = courseProgressService.updateProgress(studentId, courseModuleLessonId, progressRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProgress);
    }

    @GetMapping("/{studentId}/{courseModuleLessonId}")
    public ResponseEntity<CourseProgressResponseDTO> getProgress(
            @PathVariable Long studentId,
            @PathVariable Long courseModuleLessonId) {

        CourseProgressResponseDTO progress = courseProgressService.getProgress(studentId, courseModuleLessonId);
        return ResponseEntity.status(HttpStatus.OK).body(progress);
    }
}
