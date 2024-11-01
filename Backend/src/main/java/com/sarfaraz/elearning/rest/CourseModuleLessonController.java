package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.CourseModuleLessonRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseModuleLessonResponseDTO;
import com.sarfaraz.elearning.service.CourseModuleLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/lessons")
public class CourseModuleLessonController {

    @Autowired
    private CourseModuleLessonService courseModuleLessonService;

    @PostMapping
    public ResponseEntity<List<CourseModuleLessonResponseDTO>> addLesson(
            @PathVariable Long moduleId, @RequestBody List<CourseModuleLessonRequestDTO> requests) {
        List<CourseModuleLessonResponseDTO> responses = courseModuleLessonService.addLesson(moduleId, requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }


    @GetMapping
    public ResponseEntity<List<CourseModuleLessonResponseDTO>> getAllLessons(@PathVariable Long moduleId) {
        List<CourseModuleLessonResponseDTO> response = courseModuleLessonService.getAllLessons(moduleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<CourseModuleLessonResponseDTO> updateLesson(
            @PathVariable Long moduleId, @PathVariable Long lessonId, @RequestBody CourseModuleLessonRequestDTO request) {
        CourseModuleLessonResponseDTO response = courseModuleLessonService.updateLesson(lessonId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        courseModuleLessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}

