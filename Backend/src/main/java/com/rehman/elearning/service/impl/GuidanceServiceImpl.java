package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Guidance;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.GuidanceRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.GuidanceRequestDTO;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponseDTO;
import com.rehman.elearning.service.GuidanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuidanceServiceImpl implements GuidanceService {

    private static final Logger logger = LoggerFactory.getLogger(GuidanceServiceImpl.class);

    @Autowired
    private GuidanceRepository guidanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public GuidanceResponseDTO provideGuidance(GuidanceRequestDTO guidanceRequestDTO, Long userId) {
        logger.info("Starting guidance process for student with ID: {}", userId);

        // Fetch the student from the database
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("Student with ID {} not found", userId);
                    return new RuntimeException("Student not found");
                });

        logger.info("Student with ID {} found: {}", userId, student.getUser().getName());

        Guidance guidance = new Guidance();
        guidance.setStudent(student);
        guidance.setCreatedBy(UserCreatedBy.Self);
        guidance.setQuestion(guidanceRequestDTO.getQuestion());
        logger.info("Guidance question received: {}", guidanceRequestDTO.getQuestion());

        // Fetch recommended courses based on the question or other criteria
        List<Course> recommendedCourses = courseRepository.searchCoursesByKeyword(guidanceRequestDTO.getQuestion());
        logger.info("Courses found for keyword '{}': {}", guidanceRequestDTO.getQuestion(), recommendedCourses.size());

        recommendedCourses.forEach(course -> logger.info("Recommended course: {} - {}", course.getTitle(), course.getDescription()));

        List<GuidanceResponseDTO.CourseRecommendation> courseRecommendations = recommendedCourses.stream()
                .map(course -> new GuidanceResponseDTO.CourseRecommendation(course.getTitle(), course.getDescription()))
                .collect(Collectors.toList());

        guidance.setAnswer("Recommendations generated.");
        guidance.setCourses(recommendedCourses);
        guidanceRepository.save(guidance);
        logger.info("Guidance entry saved for student ID {}", userId);

        return new GuidanceResponseDTO(courseRecommendations);
    }
}
