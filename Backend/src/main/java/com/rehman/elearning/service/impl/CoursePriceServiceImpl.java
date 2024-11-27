package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CoursePrice;
import com.rehman.elearning.repository.CoursePriceRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.rehman.elearning.service.CoursePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursePriceServiceImpl implements CoursePriceService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CoursePriceRepository coursePriceRepository;

    @Override
    public CoursePriceResponseDTO setCoursePrice(Long courseId, CoursePriceRequestDTO request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        CoursePrice coursePrice = new CoursePrice();
        coursePrice.setCourse(course);
        coursePrice.setAmount(request.getPrice());
        coursePrice.setCurrency(request.getCurrency());
        coursePrice.setCreatedBy(UserCreatedBy.Teacher);
        coursePrice = coursePriceRepository.save(coursePrice);

        return convertToCoursePriceResponseDTO(coursePrice);
    }


    // update price code here:
    @Override
    public CoursePriceResponseDTO updateCoursePrice(Long courseId, CoursePriceRequestDTO request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        CoursePrice coursePrice = coursePriceRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        coursePrice.setAmount(request.getPrice());
        coursePrice.setCurrency(request.getCurrency());
        coursePrice = coursePriceRepository.save(coursePrice);

        return convertToCoursePriceResponseDTO(coursePrice);
    }

    private CoursePriceResponseDTO convertToCoursePriceResponseDTO(CoursePrice coursePrice) {
        CoursePriceResponseDTO dto = new CoursePriceResponseDTO();
        dto.setPrice(coursePrice.getAmount());
        dto.setCurrency(coursePrice.getCurrency());
        return dto;
    }
}
