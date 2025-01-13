package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.model.MCQ;
import com.rehman.elearning.repository.CourseModuleLessonRepository;
import com.rehman.elearning.repository.CourseModuleRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.MCQRepository;
import com.rehman.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.rehman.elearning.rest.dto.inbound.MCQRequestDTO;
import com.rehman.elearning.rest.dto.outbound.MCQResponseDTO;
import com.rehman.elearning.service.CourseModuleService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CourseModuleServiceImpl implements CourseModuleService {

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MCQRepository mcqRepository;

    @Autowired
    private CourseModuleLessonRepository lessonRepository;

    @Value("${assignment.upload.dir}")
    private String uploadDir;

    @Value("${assignment.server.url}")
    private String serverUrl;

    private static final Logger logger = LoggerFactory.getLogger(CourseModuleServiceImpl.class);

    // Adding modules for a course

    @Override
    @Transactional
    public List<CourseModuleResponseDTO> addModules(Long courseId, List<CourseModuleRequestDTO> requests) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.COURSE_NOT_FOUND));

        // Fetch the highest priority of existing modules
        Integer highestPriority = courseModuleRepository.findTopByCourseOrderByPriorityDesc(course)
                .map(CourseModule::getPriority)
                .orElse(0);

        List<CourseModuleResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseModuleRequestDTO request : requests) {
            CourseModule module = new CourseModule();
            module.setHeading(request.getHeading());
            module.setDescription(request.getDescription());
            module.setPriority(++highestPriority); // Increment the highest priority for each new module
            module.setCourse(course);
            module.setCreatedBy(UserCreatedBy.Teacher);

            module = courseModuleRepository.save(module);
            responseDTOs.add(convertToResponseDTO(module));
        }

        return responseDTOs;
    }

    // Fetching all modules for a course

    @Override
    @Transactional
    public List<CourseModuleResponseDTO> getAllModules(Long courseId) {
        List<CourseModule> modules = courseModuleRepository.findByCourseId(courseId);
        return modules.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Updating a module
    @Override
    @Transactional
    public List<CourseModuleResponseDTO> updateModules(Long moduleId, List<CourseModuleRequestDTO> requests) {
        List<CourseModuleResponseDTO> responseDTOs = new ArrayList<>();
        for (CourseModuleRequestDTO request : requests) {
            // Ensure we're updating the correct module
            CourseModule module = courseModuleRepository.findById(moduleId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
            module.setHeading(request.getHeading());
            module.setDescription(request.getDescription());
            module.setCreatedBy(UserCreatedBy.Teacher);

            module = courseModuleRepository.save(module);
            responseDTOs.add(convertToResponseDTO(module));
        }

        return responseDTOs;
    }

    @Override
    @Transactional
    public void deleteModule(Long moduleId) {
        // Fetch the module and ensure it's present
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Fetch the course explicitly to attach it to the persistence context
        Course course = module.getCourse();
        if (course != null) {
            course = courseRepository.findById(course.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

            // Properly unlink the module from the course
            course.getcourseModule().remove(module);
            module.setCourse(null); // Break the bi-directional link explicitly

            courseRepository.save(course); // Save the course to update the persistence context
        }

        // Delete lessons linked to the module
        lessonRepository.deleteAllByCourseModule(module); //cascading being here!

        // Delete the module itself
        courseModuleRepository.delete(module);
    }




    // Saving MCQs for a module

    @Override
    @Transactional
    public void saveMCQs(Long moduleId, List<MCQRequestDTO> mcqRequestDTOs) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        List<MCQ> mcqs = mcqRequestDTOs.stream().map(dto -> {
            MCQ mcq = new MCQ();
            mcq.setQuestion(dto.getQuestion());
            mcq.setOptions(dto.getOptions());
            mcq.setCorrectAnswer(dto.getCorrectAnswer());
            mcq.setCourseModule(module);
            return mcq;
        }).collect(Collectors.toList());

        mcqRepository.saveAll(mcqs);
    }

    @Override
    @Transactional
    public MCQResponseDTO getMCQById(Long moduleId, Long mcqId) {
        return mcqRepository.findByIdAndCourseModuleId(mcqId, moduleId)
                .map(mcq -> new MCQResponseDTO(mcq.getId(), mcq.getQuestion(), mcq.getOptions(), mcq.getCorrectAnswer()))
                .orElseThrow(() -> new RuntimeException("MCQ not found"));
    }

    // Updating an MCQ for a module
    @Override
    @Transactional
    public MCQResponseDTO updateMCQ(Long moduleId, Long mcqId, MCQRequestDTO request) {
        // Fetch the CourseModule by ID
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Fetch the MCQ by ID
        MCQ mcq = mcqRepository.findById(mcqId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Ensure the MCQ belongs to the correct module (optional check)
        if (!mcq.getCourseModule().getId().equals(moduleId)) {
            throw new IllegalArgumentException("MCQ does not belong to this module.");
        }

        // Update the MCQ fields
        mcq.setQuestion(request.getQuestion());
        mcq.setOptions(request.getOptions());
        mcq.setCorrectAnswer(request.getCorrectAnswer());

        // Save the updated MCQ
        mcq = mcqRepository.save(mcq);

        // Return the updated MCQ response DTO
        return new MCQResponseDTO(mcq.getId(), mcq.getQuestion(), mcq.getOptions(), mcq.getCorrectAnswer());
    }


    // Fetching all MCQs for a module
    @Override
    @Transactional
    public List<MCQResponseDTO> getMCQsForModule(Long moduleId) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        List<MCQ> mcqs = mcqRepository.findAllByCourseModule(module);
        return mcqs.stream()
                .map(mcq -> new MCQResponseDTO(mcq.getId(), mcq.getQuestion(), mcq.getOptions(), mcq.getCorrectAnswer()))
                .collect(Collectors.toList());
    }

    // Deleting an MCQ from a module


    @Override
    @Transactional
    public void deleteMCQ(Long moduleId, Long mcqId) {
        MCQ mcq = mcqRepository.findById(mcqId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        mcqRepository.delete(mcq);
    }

    // Helper method to convert CourseModule to CourseModuleResponseDTO
    private CourseModuleResponseDTO convertToResponseDTO(CourseModule module) {
        CourseModuleResponseDTO dto = new CourseModuleResponseDTO();
        dto.setId(module.getId());
        dto.setHeading(module.getHeading());
        dto.setDescription(module.getDescription());
        dto.setPriority(module.getPriority());

        return dto;
    }
}