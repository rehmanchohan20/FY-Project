package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import com.sarfaraz.elearning.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-progress")
public class CourseProgressController {

    @Autowired
    private CourseProgressService courseProgressService;

    @PostMapping
    public ResponseEntity<CourseProgressResponseDTO> createCourseProgress(@RequestBody CourseProgressRequestDTO courseProgressRequestDTO) {
        CourseProgressResponseDTO courseProgress = courseProgressService.createCourseProgress(courseProgressRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseProgress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseProgressResponseDTO> getCourseProgressById(@PathVariable Long id) {
        CourseProgressResponseDTO courseProgress = courseProgressService.getCourseProgressById(id);
        return ResponseEntity.ok(courseProgress);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseProgressResponseDTO> updateCourseProgress(@PathVariable Long id, @RequestBody CourseProgressRequestDTO courseProgressRequestDTO) {
        CourseProgressResponseDTO courseProgress = courseProgressService.updateCourseProgress(id, courseProgressRequestDTO);
        return ResponseEntity.ok(courseProgress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long id) {
        courseProgressService.deleteCourseProgress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourseProgressResponseDTO>> getAllCourseProgress() {
        List<CourseProgressResponseDTO> courseProgressList = courseProgressService.getAllCourseProgress();
        return ResponseEntity.ok(courseProgressList);
    }
}

