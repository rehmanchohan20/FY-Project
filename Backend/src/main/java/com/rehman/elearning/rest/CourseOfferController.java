package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.CourseOfferRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseOfferResponseDTO;
import com.rehman.elearning.service.CourseOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/offers")
public class CourseOfferController {

    @Autowired
    private CourseOfferService courseOfferService;

    @PostMapping
    public ResponseEntity<CourseOfferResponseDTO> createOffer(
            @PathVariable Long courseId, @RequestBody CourseOfferRequestDTO request) {
        CourseOfferResponseDTO response = courseOfferService.createOffer(courseId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CourseOfferResponseDTO>> getAllOffers(@PathVariable Long courseId) {
        List<CourseOfferResponseDTO> response = courseOfferService.getAllOffers(courseId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<CourseOfferResponseDTO> updateOffer(
            @PathVariable Long courseId, @PathVariable Long offerId, @RequestBody CourseOfferRequestDTO request) {
        CourseOfferResponseDTO response = courseOfferService.updateOffer(offerId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long offerId) {
        courseOfferService.deleteOffer(offerId);
        return ResponseEntity.noContent().build();
    }
}
