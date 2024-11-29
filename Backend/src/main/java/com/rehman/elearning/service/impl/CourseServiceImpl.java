package com.rehman.elearning.service.impl;

import com.rehman.elearning.config.JwtTokenExtractor;
import com.rehman.elearning.constants.CourseStatusEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CoursePrice;
import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.TeacherRepository;
import com.rehman.elearning.repository.UserRepository;
import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.rehman.elearning.service.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JwtTokenExtractor jwtTokenExtractor;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        String teacherIdFromToken = jwtTokenExtractor.extractTeacherIdFromJwt();
        Long teacherId = Long.parseLong(teacherIdFromToken);

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(CourseStatusEnum.DRAFT);
        course.setCreatedBy(UserCreatedBy.Teacher);
        course.setTeacher(teacher);

        CoursePrice coursePrice = new CoursePrice();
        coursePrice.setAmount(request.getCoursePrice().getPrice());
        coursePrice.setCurrency(request.getCoursePrice().getCurrency());
        coursePrice.setCreatedBy(UserCreatedBy.Teacher);
        coursePrice.setCourse(course);
        course.setCoursePrice(coursePrice);


        course = courseRepository.save(course);
        return convertToResponseDTO(course);
    }

    @Override
    public CourseResponseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        return convertToResponseDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Long courseId, CourseRequestDTO request) {
        // Retrieve existing course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Update course details
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(CourseStatusEnum.DRAFT);

        // Update course price
        CoursePrice coursePrice = course.getCoursePrice(); // Get existing course price
        if (coursePrice == null) {
            coursePrice = new CoursePrice();
            coursePrice.setCourse(course); // Set course reference
        }
        coursePrice.setAmount(request.getCoursePrice().getPrice());
        coursePrice.setCurrency(request.getCoursePrice().getCurrency());
        coursePrice.setCreatedBy(UserCreatedBy.Teacher); // Assume the course price is created by the teacher
        course.setCoursePrice(coursePrice); // Associate price with course

        // Save updated course
        course = courseRepository.save(course);
        return convertToResponseDTO(course);
    }

    @Override
    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND);
        }
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<CourseResponseDTO> searchCourseByName(String courseName) {
        List<Course> courses = courseRepository.findByTitleContaining(courseName);
        return courses.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesByKeyword(String keyword) {
        return courseRepository.searchCoursesByKeyword(keyword);
    }

    private CourseResponseDTO convertToResponseDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStatus(CourseStatusEnum.DRAFT);

        if (course.getCoursePrice() != null) {
            CoursePriceResponseDTO priceResponse = new CoursePriceResponseDTO();
            priceResponse.setPrice(course.getCoursePrice().getAmount());
            priceResponse.setCurrency(course.getCoursePrice().getCurrency());
            dto.setCoursePrice(priceResponse);
        }

        return dto;
    }
}
