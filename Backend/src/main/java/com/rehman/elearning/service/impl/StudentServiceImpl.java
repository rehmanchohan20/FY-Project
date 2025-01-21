package com.rehman.elearning.service.impl;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.model.User;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.CoursePriceRequestDTO;
import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.inbound.StudentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CoursePriceResponseDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.StudentResponseDTO;
import com.rehman.elearning.rest.dto.outbound.UserResponseDTO;
import com.rehman.elearning.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDto) {
        Student student = new Student();
        return getStudentResponseDTO(studentRequestDto, student);
    }


    @Override
    @Transactional
    public List<CourseResponseDTO> getEnrolledCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        List<Course> enrolledCourses = courseRepository.findByStudents_UserId(studentId);
        return enrolledCourses.stream()
                .map(course -> new CourseResponseDTO(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        new CoursePriceResponseDTO(course.getCoursePrice().getAmount(), course.getCoursePrice().getCurrency()),
                        course.getStatus(),
                        course.getThumbnail(),
                        course.getCategory(),
                        new UserResponseDTO(
                                course.getTeacher().getUserId(),
                                course.getTeacher().getUser().getFullName(),
                                course.getTeacher().getUser().getEmail(),
                                course.getTeacher().getUser().getImage(),
                                course.getTeacher().getUser().isTeacher() ? "Teacher" : "Student"
                        )
                ))
                .collect(Collectors.toList());
    }

    private StudentResponseDTO getStudentResponseDTO(StudentRequestDTO studentRequestDto, Student student) {
        if (studentRequestDto.getCourseIds() != null) {
            try {
                enrollStudentInCourses(student, new ArrayList<>(studentRequestDto.getCourseIds()));
            } catch (RuntimeException e) {
                logger.error("Error enrolling student in courses: {}", e.getMessage());
                throw e;
            }
        }

        Student savedStudent = studentRepository.save(student);
        return mapToResponse(savedStudent);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // Update fields
        // (Assume there are methods to update the fields, not shown here for brevity)

        // Enroll the student in the course if provided
        return getStudentResponseDTO(studentRequestDto, student);
    }

    @Override
    @Transactional
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToResponse(student);
    }

    @Override
    @Transactional
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private void enrollStudentInCourses(Student student, List<Long> courseIds) {
        Set<Long> courseIdsSet = new HashSet<>(courseIds);
        List<Course> courses = courseRepository.findByIdIn(courseIdsSet);

        if (!student.getCourses().contains(courses)) {
            student.getCourses().add((Course) courses);
        }


        if (!courses.isEmpty()) {
            for (Course course : courses) {
                student.getCourses().add(course);  // Add the course to the student’s course list
                course.getStudents().add(student); // Add the student to the course's student list
            }
            studentRepository.save(student);  // Save the student with the updated course list
            courseRepository.saveAll(courses); // Save all courses in one transaction
            logger.info("Student {} enrolled in courses: {}", student.getUserId(), courseIdsSet);
        } else {
            logger.error("One or more courses not found for IDs: {}", courseIdsSet);
            throw new RuntimeException("One or more courses not found");
        }
    }


    private StudentResponseDTO mapToResponse(Student student) {
        StudentResponseDTO responseDto = new StudentResponseDTO();
        responseDto.setUserId(student.getUserId());
        responseDto.setUser(mapToUserResponseDTO(student.getUser()));
        responseDto.setCourses(student.getCourses().stream().map(this::mapToCourseResponseDTO).collect(Collectors.toSet()));
        // Set other fields
        return responseDto;
    }

    private CourseResponseDTO mapToCourseResponseDTO(Course course) {
        return getCourseResponseDTO(course);
    }

    static CourseResponseDTO getCourseResponseDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStatus(course.getStatus());
        dto.setThumbnail(course.getThumbnail());
        dto.setCategory(course.getCategory());
        if (course.getCoursePrice() != null) {
            CoursePriceResponseDTO priceResponse = new CoursePriceResponseDTO();
            priceResponse.setPrice(course.getCoursePrice().getAmount());
            priceResponse.setCurrency(course.getCoursePrice().getCurrency());
            dto.setCoursePrice(priceResponse);
        }
        dto.setInstructor( new UserResponseDTO(
                course.getTeacher().getUserId(),
                course.getTeacher().getUser().getFullName(),
                course.getTeacher().getUser().getEmail(),
                course.getTeacher().getUser().isTeacher() ? "Teacher" : "Student",
                course.getTeacher().getUser().getImage()
        )); // Map the teacher to UserResponseDTO);
        return dto;
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        // Map User fields to UserResponseDTO fields
        return userResponseDTO;
    }
}