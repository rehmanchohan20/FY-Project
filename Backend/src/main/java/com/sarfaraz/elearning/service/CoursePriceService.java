package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CoursePriceResponseDTO;

public interface CoursePriceService {
    CoursePriceResponseDTO setCoursePrice(Long courseId, CoursePriceRequestDTO request);

    // update price code here!

}

