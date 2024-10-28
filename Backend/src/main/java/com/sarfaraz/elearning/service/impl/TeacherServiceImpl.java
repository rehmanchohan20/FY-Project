package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.model.Course;
import com.sarfaraz.elearning.model.Teacher;
import com.sarfaraz.elearning.model.User;
import com.sarfaraz.elearning.repository.CourseRepository;
import com.sarfaraz.elearning.repository.TeacherRepository;
import com.sarfaraz.elearning.repository.UserRepository;
import com.sarfaraz.elearning.rest.dto.inbound.TeacherRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseResponseDTO;
import com.sarfaraz.elearning.rest.dto.outbound.TeacherResponseDTO;
import com.sarfaraz.elearning.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDto) {
        // Fetch the user by userId from the repository
        User user = userRepository.findById(teacherRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Teacher teacher = new Teacher();
        teacher.setUser(user);

        // Fetch and assign courses by courseIds
        Set<Course> courses = teacherRequestDto.getCourseIds().stream()
                .map(courseId -> courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Course not found")))
                .collect(Collectors.toSet());
        teacher.setCourses(courses);

        // Save the teacher entity
        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapToResponse(savedTeacher);
    }
    @Override
    public Teacher findTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + teacherId));
    }


    @Override
    public TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO teacherRequestDto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Update user and courses if needed
        User user = userRepository.findById(teacherRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        teacher.setUser(user);

        Set<Course> courses = teacherRequestDto.getCourseIds().stream()
                .map(courseId -> courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Course not found")))
                .collect(Collectors.toSet());
        teacher.setCourses(courses);

        // Save updated teacher entity
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapToResponse(updatedTeacher);
    }

    @Override
    public TeacherResponseDTO getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return mapToResponse(teacher);
    }

    @Override
    public List<TeacherResponseDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    private TeacherResponseDTO mapToResponse(Teacher teacher) {
        TeacherResponseDTO responseDto = new TeacherResponseDTO();

        // Set userId from User
        responseDto.setUserId(teacher.getUser().getId());

        // Convert courses to CourseResponseDTO instead of just Long IDs
        responseDto.setCourses(teacher.getCourses().stream()
                .map(this::mapCourseToResponseDTO)  // Convert each course to CourseResponseDTO
                .collect(Collectors.toSet()));

        // Set other fields if needed
        return responseDto;
    }

    private CourseResponseDTO mapCourseToResponseDTO(Course course) {
        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setId(course.getId());
        courseResponseDTO.setTitle(course.getTitle());
        courseResponseDTO.setDescription(course.getDescription());
        // Set other fields from Course to CourseResponseDTO
        return courseResponseDTO;
    }

}





