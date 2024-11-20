package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.GuidanceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponseDTO;

public interface GuidanceService {
    GuidanceResponseDTO provideGuidance(GuidanceRequestDTO guidanceRequestDTO, Long studentId);
}

