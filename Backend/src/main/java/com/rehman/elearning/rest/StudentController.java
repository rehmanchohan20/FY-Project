package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.StudentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.StudentResponseDTO;
import com.rehman.elearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/public")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<StudentResponseDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/students")
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO createdStudent = studentService.createStudent(studentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @GetMapping("/students/{userId}")
    public StudentResponseDTO getStudentById(@PathVariable Long userId) {
        return studentService.getStudentById(userId);
    }

    @PutMapping("/students")
    public ResponseEntity<StudentResponseDTO> updateStudent(@AuthenticationPrincipal Jwt jwt, @RequestBody StudentRequestDTO studentRequestDTO) {
        long userId = Long.valueOf(jwt.getId());
        StudentResponseDTO updatedStudent = studentService.updateStudent(userId, studentRequestDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{userId}")
    public String deleteStudent(@PathVariable Long userId) {
        studentService.deleteStudent(userId);
        return "Student deleted successfully";
    }

    @GetMapping("/students/courses")
    public ResponseEntity<Set<CourseResponseDTO>> getEnrolledCourses(@AuthenticationPrincipal Jwt jwt) {
        long userId = Long.valueOf(jwt.getId());
        // Fetch the student by the extracted user ID
        StudentResponseDTO student = studentService.getStudentById(userId);
        // Get the enrolled courses
        Set<CourseResponseDTO> enrolledCourses = student.getCourses();
        return ResponseEntity.ok(enrolledCourses);
    }
}
