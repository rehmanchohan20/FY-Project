package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.constants.ErrorEnum;
import com.sarfaraz.elearning.constants.UserCreatedBy;
import com.sarfaraz.elearning.exceptions.ResourceNotFoundException;
import com.sarfaraz.elearning.model.Course;
import com.sarfaraz.elearning.model.CoursePrice;
import com.sarfaraz.elearning.repository.CoursePriceRepository;
import com.sarfaraz.elearning.repository.CourseRepository;
import com.sarfaraz.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.sarfaraz.elearning.service.CoursePriceService;
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

    private CoursePriceResponseDTO convertToCoursePriceResponseDTO(CoursePrice coursePrice) {
        CoursePriceResponseDTO dto = new CoursePriceResponseDTO();
        dto.setPrice(coursePrice.getAmount());
        dto.setCurrency(coursePrice.getCurrency());
        return dto;
    }
}
