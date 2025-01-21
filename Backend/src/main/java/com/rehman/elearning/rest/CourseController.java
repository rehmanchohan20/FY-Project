package com.rehman.elearning.rest;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.RecommendedCourseResponseDTO;
import com.rehman.elearning.service.CourseRecommendationService;
import com.rehman.elearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRecommendationService courseRecommendationService;

    // Create a course (without thumbnail)
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_TEACHER')")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO courseRequest) {
        CourseResponseDTO courseResponseDTO = courseService.createCourse(courseRequest); // No thumbnail here
        return new ResponseEntity<>(courseResponseDTO, HttpStatus.CREATED);
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id) {
        CourseResponseDTO response = courseService.getCourseById(id);
        return ResponseEntity.ok(response);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<CourseResponseDTO> response = courseService.getAllCourses();
        return ResponseEntity.ok(response);
    }
    //get filtered out courses for student in which he's not enrolled:
    @GetMapping("/student/{studentId}/available-courses")
    public List<CourseResponseDTO> getAvailableCourses(@PathVariable Long studentId) {
        return courseService.getAvailableCoursesForStudent(studentId);
    }

    // Update a course (without thumbnail)
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long courseId,
            @RequestBody CourseRequestDTO courseRequest) {
        CourseResponseDTO updatedCourse = courseService.updateCourse(courseId, courseRequest); // No thumbnail here
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // Search for courses by name
    @GetMapping("/search/{courseName}")
    public List<CourseResponseDTO> getCourseByName(@PathVariable String courseName) {
        return courseService.searchCourseByName(courseName);
    }

    // Separate endpoint for uploading the course thumbnail
    @PostMapping("/{courseId}/thumbnail")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_TEACHER')")
    public ResponseEntity<String> uploadThumbnail(
            @PathVariable Long courseId,
            @RequestParam("thumbnail") MultipartFile thumbnail) {
        String thumbnailUrl = courseService.uploadThumbnail(courseId, thumbnail);
        return new ResponseEntity<>(thumbnailUrl, HttpStatus.CREATED);
    }

    // Separate endpoint for updating the course thumbnail
    @PutMapping("/{courseId}/thumbnail")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_TEACHER')")
    public ResponseEntity<String> updateThumbnail(
            @PathVariable Long courseId,
            @RequestParam("thumbnail") MultipartFile thumbnail) {
        String thumbnailUrl = courseService.uploadThumbnail(courseId, thumbnail);  // Reusing the upload method
        return new ResponseEntity<>(thumbnailUrl, HttpStatus.OK);
    }

    // recommend course to the students:
    @GetMapping("/recommendations")
    public List<RecommendedCourseResponseDTO> getRecommendedCourses(@AuthenticationPrincipal Jwt jwt) {
        long studentId = Long.parseLong(jwt.getId());
        return courseRecommendationService.getRecommendedCourses(studentId);
    }

    // course categories:
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryEnum>> getAllCategories() {
        List<CategoryEnum> categories = List.of(CategoryEnum.values()); // Get all enum values from CategoryEnum
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByCategory(@PathVariable CategoryEnum category) {
        List<CourseResponseDTO> courses = courseService.getCoursesByCategory(category);
        return ResponseEntity.ok(courses);
    }
}
