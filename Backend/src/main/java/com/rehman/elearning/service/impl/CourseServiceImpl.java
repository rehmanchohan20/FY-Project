package com.rehman.elearning.service.impl;

import com.rehman.elearning.config.JwtTokenExtractor;
import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.constants.CourseStatusEnum;
import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CoursePrice;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.repository.TeacherRepository;
import com.rehman.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.rehman.elearning.rest.dto.outbound.MediaResponseDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import com.rehman.elearning.service.CourseService;
import com.rehman.elearning.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rehman.elearning.service.impl.StudentServiceImpl.getCourseResponseDTO;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JwtTokenExtractor jwtTokenExtractor;


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MediaService mediaService;  // Inject MediaService for uploading video

    @Value("${course.thumbnail.upload}")
    private String thumbnailUploadDir;

    @Value("${course.thumbnail.server}")
    private String thumbnailServerUrl;

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        String teacherIdFromToken = jwtTokenExtractor.extractTeacherIdFromJwt();
        Long teacherId = Long.parseLong(teacherIdFromToken);

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Initialize and set course details
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(CourseStatusEnum.DRAFT);
        course.setCreatedBy(UserCreatedBy.Teacher);
        course.setTeacher(teacher);
        course.setCategory(request.getCategory());

        // Save course price
        CoursePrice coursePrice = new CoursePrice();
        coursePrice.setAmount(request.getCoursePrice().getPrice());
        coursePrice.setCurrency(request.getCoursePrice().getCurrency());
        coursePrice.setCreatedBy(UserCreatedBy.Teacher);
        coursePrice.setCourse(course);
        course.setCoursePrice(coursePrice);

        // Save and return course response
        course = courseRepository.save(course);
        return convertToResponseDTO(course);
    }

    @Override
    @Transactional
    public String uploadThumbnail(Long courseId, MultipartFile thumbnail) {
        // Retrieve the course by its ID
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.COURSE_NOT_FOUND));
        try {
            // Reuse the MediaService uploadThumbnail method
            MediaResponseDTO mediaResponse = mediaService.uploadThumbnail(thumbnail);

            // Update course thumbnail URL
            course.setThumbnail(mediaResponse.getUrl());
            courseRepository.save(course); // Save the course with the updated thumbnail URL

            return mediaResponse.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(String.valueOf(ErrorEnum.FAILED_TO_UPLOAD_THUMBNAIL));
        }
    }


    @Override
    @Transactional
    public CourseResponseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        return convertToResponseDTO(course);
    }

    @Override
    @Transactional
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Long courseId, CourseRequestDTO request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setStatus(request.getStatus() != null ? request.getStatus() : CourseStatusEnum.DRAFT);
        course.setCategory(request.getCategory());

        // Update or create course price
        CoursePrice coursePrice = course.getCoursePrice();
        if (coursePrice == null) {
            coursePrice = new CoursePrice();
            coursePrice.setCourse(course);
        }
        coursePrice.setAmount(request.getCoursePrice().getPrice());
        coursePrice.setCurrency(request.getCoursePrice().getCurrency());
        coursePrice.setCreatedBy(UserCreatedBy.Teacher);
        course.setCoursePrice(coursePrice);
        course = courseRepository.save(course);
        return convertToResponseDTO(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        // Fetch the course to ensure it exists and load all related entities
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.COURSE_NOT_FOUND));

        try {
            if (course.getCoursePrice() != null) {
                course.setCoursePrice(null); // Unlink the price from the course
            }
            if (course.getcourseModule()!= null && !course.getcourseModule().isEmpty()) {
                course.getcourseModule().forEach(module -> module.setCourse(null)); // Unlink each module
                course.getcourseModule().clear(); // Clear the collection
            }

            // Finally, delete the course itself
            if(course != null && course.getTeacher() != null) {
                course.getTeacher().getCourses().remove(course); // Unlink the course from the teacher
            }
            courseRepository.delete(course); // Delete the course object
            courseRepository.flush(); // Ensure delete it immediately executed

            // Log success
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete course with ID: " + courseId, e);
        }
    }

    @Override
    public List<CourseResponseDTO> searchCourseByName(String courseName) {
        return courseRepository.findByTitleContaining(courseName).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getCoursesByKeyword(String keyword) {
        return courseRepository.searchCoursesByKeyword(keyword);
    }

    public List<CourseResponseDTO> getCoursesByCategory(CategoryEnum category) {
        List<Course> courses = courseRepository.findByCategory(category);
        return courses.stream()
                .map(course -> {
                    // Assuming coursePrice is an embedded object and needs to be mapped
                    CoursePriceResponseDTO coursePrice = new CoursePriceResponseDTO(
                            course.getCoursePrice().getAmount(),
                            course.getCoursePrice().getCurrency()
                    );
                    return new CourseResponseDTO(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            coursePrice,
                            course.getStatus(),
                            course.getThumbnail(),
                            course.getCategory(),
                            new UserResponseDTO(course.getTeacher().getUser().getFullName())
                    );

                })
                .collect(Collectors.toList());
    }

    private CourseResponseDTO convertToResponseDTO(Course course) {
        return getCourseResponseDTO(course);
    }

    @Override
    @Transactional
    public List<CourseRequestDTO> getAvailableCoursesForStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID."));

        List<Course> allCourses = courseRepository.findAll();
        Set<Course> enrolledCoursesSet = student.getCourses();  // This is a Set
        // Convert the Set to a List
        List<Course> enrolledCourses = new ArrayList<>(enrolledCoursesSet);

        // Filter out the courses the student is already enrolled in and map to CourseRequestDTO
        return allCourses.stream()
                .filter(course -> !enrolledCourses.contains(course))
                .map(course -> new CourseRequestDTO(
                        course.getTitle(),
                        course.getDescription(),
                        new CoursePriceRequestDTO(course.getCoursePrice().getAmount(), course.getCoursePrice().getCurrency()),
                        course.getStatus(),
                        course.getCategory()
                ))
                .collect(Collectors.toList());
    }

}