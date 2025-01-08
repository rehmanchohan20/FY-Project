package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseModuleLessonRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleLessonResponseDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.service.CourseModuleLessonService;
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

    @GetMapping("/{lessonId}/media")
    public ResponseEntity<List<MediaResponseDTO>> getLessonMedia(@PathVariable Long lessonId) {
        List<MediaResponseDTO> mediaList = courseModuleLessonService.getMediaByLessonId(lessonId);
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping
    public ResponseEntity<List<CourseModuleLessonResponseDTO>> getAllLessons(@PathVariable Long moduleId) {
        List<CourseModuleLessonResponseDTO> response = courseModuleLessonService.getAllLessons(moduleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<CourseModuleLessonResponseDTO> updateLesson(
            @PathVariable Long lessonId, @RequestBody CourseModuleLessonRequestDTO request) {
        CourseModuleLessonResponseDTO response = courseModuleLessonService.updateLesson(lessonId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        courseModuleLessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}

