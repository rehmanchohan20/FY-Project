package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.MCQRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MCQResponseDTO;
import com.rehman.elearning.service.CourseModuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MCQsController {

    @Autowired
    private CourseModuleService courseModuleService;

    // MCQ CRUD operations
    @PostMapping("/{moduleId}/mcqs")
    public ResponseEntity<Void> addMCQsToModule(
            @PathVariable Long moduleId, @Valid @RequestBody List<MCQRequestDTO> mcqRequestDTOs) {
        courseModuleService.saveMCQs(moduleId, mcqRequestDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/{moduleId}/mcqs")
    public ResponseEntity<List<MCQResponseDTO>> getMCQs(@PathVariable Long moduleId) {
        List<MCQResponseDTO> mcqs = courseModuleService.getMCQsForModule(moduleId);
        return ResponseEntity.ok(mcqs);
    }

    @PutMapping("/{moduleId}/mcqs/{mcqId}")
    public ResponseEntity<MCQResponseDTO> updateMCQ(
            @PathVariable Long courseId, @PathVariable Long moduleId, @PathVariable Long mcqId, @RequestBody MCQRequestDTO request) {
        MCQResponseDTO updatedMCQ = courseModuleService.updateMCQ(moduleId, mcqId, request);
        return ResponseEntity.ok(updatedMCQ);
    }

    @DeleteMapping("/{moduleId}/mcqs/{mcqId}")
    public ResponseEntity<Void> deleteMCQ(@PathVariable Long moduleId, @PathVariable Long mcqId) {
        courseModuleService.deleteMCQ(moduleId, mcqId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
