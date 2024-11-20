package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.CourseOfferRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseOfferResponseDTO;

import java.util.List;

public interface CourseOfferService {
    CourseOfferResponseDTO createOffer(Long courseId, CourseOfferRequestDTO request);
    List<CourseOfferResponseDTO> getAllOffers(Long courseId);
    CourseOfferResponseDTO updateOffer(Long offerId, CourseOfferRequestDTO request);
    void deleteOffer(Long offerId);
}

