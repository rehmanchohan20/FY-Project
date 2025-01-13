package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private StudentService enrollmentService;

    @GetMapping("/student")
    public ResponseEntity<List<CourseResponseDTO>> getEnrolledCourses(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.valueOf(jwt.getId());
        List<CourseResponseDTO> courses = enrollmentService.getEnrolledCourses(userId);
        return ResponseEntity.ok(courses);
    }
}