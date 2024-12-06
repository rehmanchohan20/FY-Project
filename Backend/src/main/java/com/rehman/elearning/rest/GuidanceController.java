package com.rehman.elearning.rest;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.rest.dto.inbound.GuidanceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponseDTO;
import com.rehman.elearning.service.GuidanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guidance")
public class GuidanceController {

    @Autowired
    private GuidanceService guidanceService;

    @PostMapping("/ask-question")
    public ResponseEntity<GuidanceResponseDTO> askQuestion(
            @AuthenticationPrincipal Jwt jwt, // Automatically inject the JWT token
            @RequestBody GuidanceRequestDTO guidanceRequestDTO) {

        // Extract studentId from JWT token
        long studentId = Long.valueOf(jwt.getId()); // Assuming studentId is stored in the 'id' field of JWT

        // Optionally, you can still use the studentId from the request body
        if (guidanceRequestDTO.getStudentId() != null && guidanceRequestDTO.getStudentId() != studentId) {
            // Optional validation: ensure the studentId in the request matches the one in the JWT token
            return ResponseEntity.status(400).body(null);
        }

        // Get other details from the request body
        String question = guidanceRequestDTO.getQuestion();
        List<Course> courses = guidanceRequestDTO.getCourses();

        // Fetch the Student object using the studentId (You can modify this part to get student from DB if needed)
        Student student = new Student();
        student.setUserId(studentId);

        // Create and return the guidance response
        GuidanceResponseDTO response = guidanceService.createGuidanceForStudent(student, question, courses);
        return ResponseEntity.ok(response);
    }
}
