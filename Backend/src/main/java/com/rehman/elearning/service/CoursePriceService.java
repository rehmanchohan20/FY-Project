package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CoursePriceResponseDTO;

public interface CoursePriceService {
    CoursePriceResponseDTO setCoursePrice(Long courseId, CoursePriceRequestDTO request);

    // update price code here!

}

