package com.rehman.elearning.rest;


import com.rehman.elearning.rest.dto.inbound.CertificationDTO;
import com.rehman.elearning.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @PostMapping("/issue")
    public ResponseEntity<CertificationDTO> issueCertification(@RequestParam Long studentId, @RequestParam Long courseId) {
        CertificationDTO certification = certificationService.issueCertification(studentId, courseId);
        return ResponseEntity.ok(certification);
    }
}
