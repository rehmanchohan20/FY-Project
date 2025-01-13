package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import com.rehman.elearning.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course-progress")
public class CourseProgressController {

    @Autowired
    private CourseProgressService courseProgressService;

    @PostMapping("/{courseModuleLessonId}/update")
    public ResponseEntity<CourseProgressResponseDTO> updateProgress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long courseModuleLessonId,
            @RequestBody CourseProgressRequestDTO progressRequest) {

        Long studentId = Long.parseLong(jwt.getId());
        CourseProgressResponseDTO updatedProgress = courseProgressService.updateProgress(studentId, courseModuleLessonId, progressRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProgress);
    }

    @GetMapping("/{courseModuleLessonId}")
    public ResponseEntity<CourseProgressResponseDTO> getProgress(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long courseModuleLessonId) {
        long studentId = Long.parseLong(jwt.getId());
        CourseProgressResponseDTO progress = courseProgressService.getProgress(studentId, courseModuleLessonId);
        return ResponseEntity.status(HttpStatus.OK).body(progress);
    }
}