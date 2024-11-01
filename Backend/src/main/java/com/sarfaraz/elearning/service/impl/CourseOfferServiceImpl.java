package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.constants.ErrorEnum;
import com.sarfaraz.elearning.constants.UserCreatedBy;
import com.sarfaraz.elearning.exceptions.ResourceNotFoundException;
import com.sarfaraz.elearning.model.Course;
import com.sarfaraz.elearning.model.CourseOffer;
import com.sarfaraz.elearning.repository.CourseOfferRepository;
import com.sarfaraz.elearning.repository.CourseRepository;
import com.sarfaraz.elearning.rest.dto.inbound.CourseOfferRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseOfferResponseDTO;
import com.sarfaraz.elearning.service.CourseOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseOfferServiceImpl implements CourseOfferService {

    @Autowired
    private CourseOfferRepository courseOfferRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseOfferResponseDTO createOffer(Long courseId, CourseOfferRequestDTO request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        CourseOffer offer = new CourseOffer();
        offer.setDiscount(request.getDiscount());
        offer.setStartDate(request.getStartDate());
        offer.setEndDate(request.getEndDate());
        offer.setCreatedBy(UserCreatedBy.Teacher);
        offer.setCourse(course);
        offer = courseOfferRepository.save(offer);

        return convertToResponseDTO(offer);
    }

    @Override
    public List<CourseOfferResponseDTO> getAllOffers(Long courseId) {
        List<CourseOffer> offers = courseOfferRepository.findByCourseId(courseId);
        return offers.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseOfferResponseDTO updateOffer(Long offerId, CourseOfferRequestDTO request) {
        CourseOffer offer = courseOfferRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        offer.setDiscount(request.getDiscount());
        offer.setStartDate(request.getStartDate());
        offer.setEndDate(request.getEndDate());
        offer = courseOfferRepository.save(offer);
        return convertToResponseDTO(offer);
    }

    @Override
    public void deleteOffer(Long offerId) {
        courseOfferRepository.deleteById(offerId);
    }

    private CourseOfferResponseDTO convertToResponseDTO(CourseOffer offer) {
        CourseOfferResponseDTO dto = new CourseOfferResponseDTO();
        dto.setId(offer.getCourseId());
        dto.setDiscount(offer.getDiscount());
        dto.setStartDate(offer.getStartDate());
        dto.setEndDate(offer.getEndDate());
        return dto;
    }
}

