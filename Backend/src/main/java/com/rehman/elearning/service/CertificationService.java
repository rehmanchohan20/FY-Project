package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CertificationDTO;

public interface CertificationService {
    CertificationDTO issueCertification(Long studentId, Long courseId);
}