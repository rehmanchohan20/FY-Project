package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.CourseRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseResponseDTO;
import com.sarfaraz.elearning.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO courseRequestDto) {
        CourseResponseDTO createdCourse = courseService.createCourse(courseRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/courses/{courseId}")
    public String updateCourse(@PathVariable Long courseId, @RequestBody CourseRequestDTO courseRequestDTO) {
        courseService.updateCourse(courseId, courseRequestDTO);
        return "Course updated successfully";
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return "Course deleted successfully";
    }

    @GetMapping("/courses/search/{courseName}")
    public List<CourseResponseDTO> getCourseByName(@PathVariable String courseName) {
        return courseService.searchCourseByName(courseName);
    }
}
