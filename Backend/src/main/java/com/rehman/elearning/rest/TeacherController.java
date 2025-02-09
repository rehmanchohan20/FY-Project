package com.rehman.elearning.rest;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.rest.dto.inbound.TeacherRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.TeacherResponseDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import com.rehman.elearning.service.CourseService;
import com.rehman.elearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/teachers")
    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/teachers/{teacherId}/courses")
    public List<CourseResponseDTO> getCoursesByTeacher(@PathVariable Long teacherId) {
        return teacherService.getCoursesByTeacher(teacherId);
    }

//    @GetMapping("/teachers/me/courses")
//    public ResponseEntity<List<CourseResponseDTO>> getMyCourses(@AuthenticationPrincipal Jwt jwt) {
//        // Extract the user ID from the JWT token
//        Long userId = Long.valueOf(jwt.getClaim("jti")); // Adjust this based on your JWT structure
//
//        return ResponseEntity.ok(userId);
//    }


    @PostMapping("/teachers")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO createdTeacher = teacherService.createTeacher(teacherRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher);
    }

    @GetMapping("/teachers/{userId}")
    public TeacherResponseDTO getTeacherById(@PathVariable Long userId) {
        return teacherService.getTeacherById(userId);
    }

    @PutMapping("/teachers/{userId}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long userId, @RequestBody TeacherRequestDTO teacherRequestDTO) {
        TeacherResponseDTO updatedTeacher = teacherService.updateTeacher(userId, teacherRequestDTO);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/teachers/{userId}")
    public String deleteTeacher(@PathVariable Long userId) {
        teacherService.deleteTeacher(userId);
        return "Teacher deleted successfully";
    }
}
