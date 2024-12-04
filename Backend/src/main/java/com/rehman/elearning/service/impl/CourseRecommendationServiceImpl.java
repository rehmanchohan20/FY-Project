package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.outbound.RecommendedCourseResponseDTO;
import com.rehman.elearning.service.CourseRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseRecommendationServiceImpl implements CourseRecommendationService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<RecommendedCourseResponseDTO> getRecommendedCourses(Long studentId) {
        var student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException(ErrorEnum.STUDENT_NOT_FOUND.getCode()));
        return courseRepository.findByStudentsNotContaining(student)
                .stream()
                .map(this::mapToRecommendedCourseDTO)
                .collect(Collectors.toList());
    }

    private RecommendedCourseResponseDTO mapToRecommendedCourseDTO(Course course) {
        return new RecommendedCourseResponseDTO(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getThumbnail(),
                course.getTeacher().getUser().getName(),
                course.getCoursePrice().getAmount().toString(),
                course.getCoursePrice().getCurrency(),
                course.getCategory()
        );
    }
}
