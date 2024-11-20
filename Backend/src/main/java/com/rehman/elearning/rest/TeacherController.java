package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.TeacherRequestDTO;
import com.rehman.elearning.rest.dto.outbound.TeacherResponseDTO;
import com.rehman.elearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teachers")
    public List<TeacherResponseDTO> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

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
