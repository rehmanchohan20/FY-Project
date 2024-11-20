package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.StudentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.StudentResponseDTO;
import com.rehman.elearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/students/{userId}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long userId, @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO updatedStudent = studentService.updateStudent(userId, studentRequestDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/students/{userId}")
    public String deleteStudent(@PathVariable Long userId) {
        studentService.deleteStudent(userId);
        return "Student deleted successfully";
    }
}
