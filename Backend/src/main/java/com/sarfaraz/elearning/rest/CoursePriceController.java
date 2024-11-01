package com.sarfaraz.elearning.rest;

import com.sarfaraz.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.sarfaraz.elearning.service.CoursePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/price")
public class CoursePriceController {

    @Autowired
    private CoursePriceService coursePriceService;

    @PostMapping
    public ResponseEntity<CoursePriceResponseDTO> setCoursePrice(
            @PathVariable Long courseId, @RequestBody CoursePriceRequestDTO request) {
        CoursePriceResponseDTO response = coursePriceService.setCoursePrice(courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

