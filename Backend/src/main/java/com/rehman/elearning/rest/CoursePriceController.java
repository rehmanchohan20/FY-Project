package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.rehman.elearning.service.CoursePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/{courseId}/price")
public class CoursePriceController {

    @Autowired
    private CoursePriceService coursePriceService;

    @PutMapping()
    public ResponseEntity<CoursePriceResponseDTO> updateCoursePrice(
            @PathVariable Long courseId,
            @RequestBody CoursePriceRequestDTO request) {
        CoursePriceResponseDTO response = coursePriceService.updateCoursePrice(courseId, request);
        return ResponseEntity.ok(response);
    }
}


//    @PostMapping
//    public ResponseEntity<CoursePriceResponseDTO> setCoursePrice(
//            @PathVariable Long courseId, @RequestBody CoursePriceRequestDTO request) {
//        CoursePriceResponseDTO response = coursePriceService.setCoursePrice(courseId, request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }

