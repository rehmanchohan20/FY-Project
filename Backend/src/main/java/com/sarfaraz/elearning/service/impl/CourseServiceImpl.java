package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.constants.UserCreatedBy;
import com.sarfaraz.elearning.model.*;
import com.sarfaraz.elearning.repository.CourseRepository;
import com.sarfaraz.elearning.repository.TeacherRepository;
import com.sarfaraz.elearning.rest.dto.inbound.CourseOfferRequestDTO;
import com.sarfaraz.elearning.rest.dto.inbound.CourseRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.*;
import com.sarfaraz.elearning.service.CourseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO courseRequestDto) {
        Teacher teacher = teacherRepository.findById(courseRequestDto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = new Course();
        course.setTitle(courseRequestDto.getTitle());
        course.setDescription(courseRequestDto.getDescription());
        course.setStatus(courseRequestDto.getStatus());
        course.setTeacher(teacher);
        course.setCreatedBy(UserCreatedBy.Teacher); // Set createdBy for course

        // Handle course price
        if (courseRequestDto.getCoursePrice() != null) {
            CoursePrice coursePrice = new CoursePrice();
            coursePrice.setAmount(courseRequestDto.getCoursePrice().getPrice());
            coursePrice.setCurrency(courseRequestDto.getCoursePrice().getCurrency());
            coursePrice.setCourse(course); // Set the course reference in CoursePrice
            coursePrice.setCreatedBy(UserCreatedBy.Teacher); // Set createdBy for coursePrice
            course.setCoursePrice(coursePrice); // Assign CoursePrice to the Course
        }

        // Handle course modules
        Set<CourseModule> modules = courseRequestDto.getCourseModules().stream()
                .map(moduleDto -> {
                    CourseModule module = new CourseModule();
                    module.setHeading(moduleDto.getTitle());
                    module.setDescription(moduleDto.getContent());
                    module.setCourse(course); // Set the course reference in CourseModule
                    module.setCreatedBy(UserCreatedBy.Teacher); // Set the createdBy field

                    return module;
                }).collect(Collectors.toSet());

        // Use the method to add course modules and maintain bidirectional relationship
        for (CourseModule module : modules) {
            course.addCourseModule(module); // This handles setting the course reference in CourseModule
        }

        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse, null); // Pass null for the offer DTO since it's not needed here
    }


    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Update course properties
        course.setTitle(courseRequestDto.getTitle());
        course.setDescription(courseRequestDto.getDescription());
        course.setStatus(courseRequestDto.getStatus());

        // Update course price if present
        if (courseRequestDto.getCoursePrice() != null) {
            CoursePrice coursePrice = course.getCoursePrice();
            if (coursePrice != null) {
                coursePrice.setAmount(courseRequestDto.getCoursePrice().getPrice());
                coursePrice.setCurrency(courseRequestDto.getCoursePrice().getCurrency());
            } else {
                coursePrice = new CoursePrice();
                coursePrice.setAmount(courseRequestDto.getCoursePrice().getPrice());
                coursePrice.setCurrency(courseRequestDto.getCoursePrice().getCurrency());
                coursePrice.setCourse(course); // Link the new CoursePrice
                course.setCoursePrice(coursePrice); // Assign CoursePrice to the Course
            }
        }

        // Update course offer if present
        if (courseRequestDto.getCourseOffer() != null) {
            CourseOffer courseOffer = course.getCourseOffer();
            if (courseOffer != null) {
                courseOffer.setDiscount(courseRequestDto.getCourseOffer().getDiscount());
            } else {
                courseOffer = new CourseOffer();
                courseOffer.setDiscount(courseRequestDto.getCourseOffer().getDiscount());
                courseOffer.setCourse(course); // Link the new CourseOffer
                course.setCourseOffer(courseOffer); // Assign CourseOffer to the Course
            }
        }

        // Handle course modules
        Set<CourseModule> modules = courseRequestDto.getCourseModules().stream()
                .map(moduleDto -> {
                    CourseModule module = new CourseModule();
                    module.setHeading(moduleDto.getTitle());
                    module.setDescription(moduleDto.getContent());
                    module.setCourse(course); // Set the course reference in CourseModule
                    return module;
                }).collect(Collectors.toSet());

        course.getCourseDetails().clear(); // Clear existing modules
        for (CourseModule module : modules) {
            course.addCourseModule(module); // This handles setting the course reference in CourseModule
        }

        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse, courseRequestDto.getCourseOffer()); // Pass the offer DTO for mapping
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToResponse(course, null); // Pass null for the offer DTO since it's not needed here
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> mapToResponse(course, null)).collect(Collectors.toList());
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found");
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponseDTO> searchCourseByName(String courseName) {
        List<Course> courses = courseRepository.findByTitleContaining(courseName); // Assuming you have this method in the repository
        return courses.stream().map(course -> mapToResponse(course, null)).collect(Collectors.toList());
    }


    private CourseResponseDTO mapToResponse(Course course, CourseOfferRequestDTO courseOfferRequestDTO) {
        CourseResponseDTO responseDto = new CourseResponseDTO();
        responseDto.setId(course.getId());
        responseDto.setTitle(course.getTitle());
        responseDto.setDescription(course.getDescription());
        responseDto.setStatus(course.getStatus());

        TeacherResponseDTO teacherDTO = new TeacherResponseDTO();
        teacherDTO.setUserId(course.getTeacher().getUserId());
        teacherDTO.setUserName(course.getTeacher().getUser().getName());
        responseDto.setTeacher(teacherDTO);

        if (course.getCoursePrice() != null) {
            CoursePriceResponseDTO priceDTO = new CoursePriceResponseDTO();
            priceDTO.setId(course.getCoursePrice().getCourseId()); // Assuming you have an ID in CoursePrice
            priceDTO.setPrice(course.getCoursePrice().getAmount());
            priceDTO.setCurrency(course.getCoursePrice().getCurrency());
            responseDto.setCoursePrice(priceDTO);
        }

        if (course.getCourseOffer() != null) {
            CourseOfferResponseDTO offerDTO = new CourseOfferResponseDTO();
            offerDTO.setDiscount(course.getCourseOffer().getDiscount());
            offerDTO.setId(course.getCourseOffer().getCourse().getId());
            responseDto.setCourseOffer(offerDTO);
        }

        Set<CourseModuleResponseDTO> moduleDTOs = course.getCourseDetails().stream()
                .map(module -> {
                    CourseModuleResponseDTO moduleDTO = new CourseModuleResponseDTO();
                    moduleDTO.setId(module.getId());
                    moduleDTO.setTitle(module.getHeading());
                    moduleDTO.setContent(module.getDescription());
                    return moduleDTO;
                })
                .collect(Collectors.toSet());
        responseDto.setCourseModules(moduleDTOs);

//        Set<StudentResponseDTO> studentDTOs = course.getStudents().stream()
//                .map(student -> {
//                    StudentResponseDTO studentDTO = new StudentResponseDTO();
//                    studentDTO.setUserId(student.getUser().getId());
//                    return studentDTO;
//                })
//                .collect(Collectors.toSet());
//        responseDto.setStudents(studentDTOs);

        // Set other fields as necessary
        return responseDto;
    }
}
