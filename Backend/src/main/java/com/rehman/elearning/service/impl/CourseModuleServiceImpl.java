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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
    public List<CourseModuleResponseDTO> addModules(Long courseId, List<CourseModuleRequestDTO> requests) {
        logger.info("Adding modules for courseId: {}", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        List<CourseModuleResponseDTO> responseDTOs = requests.stream()
                .map(request -> {
                    // Check if a module with the same priority already exists
                    boolean priorityExists = courseModuleRepository.existsByCourseAndPriority(course, request.getPriority());
                    if (priorityExists) {
                        throw new IllegalArgumentException("A module with this priority already exists.");
                    }

                    CourseModule module = new CourseModule();
                    module.setHeading(request.getHeading());
                    module.setDescription(request.getDescription());
                    module.setPriority(request.getPriority());
                    module.setCourse(course);
                    module.setAssignmentPath(request.getAssignmentPath());
                    module.setCreatedBy(UserCreatedBy.Teacher);

                    module = courseModuleRepository.save(module);
                    return convertToResponseDTO(module);
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }

    // Fetching all modules for a course
    @Override
    public List<CourseModuleResponseDTO> getAllModules(Long courseId) {
        List<CourseModule> modules = courseModuleRepository.findByCourseId(courseId);
        return modules.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Updating a module
    @Override
    public CourseModuleResponseDTO updateModule(Long moduleId, CourseModuleRequestDTO request) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        module.setHeading(request.getHeading());
        module.setDescription(request.getDescription());
        module.setPriority(request.getPriority());
        module.setCreatedBy(UserCreatedBy.Teacher);
        module.setAssignmentPath(request.getAssignmentPath());
        module = courseModuleRepository.save(module);
        return convertToResponseDTO(module);
    }

    // Deleting a module
    @Override
    public void deleteModule(Long moduleId) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        // Ensure no lessons exist before deleting or cascade delete if necessary
        lessonRepository.deleteAllByCourseModule(module);  // Cascade delete the lessons if needed
        courseModuleRepository.delete(module);   // Then delete the module itself
    }

    // Uploading an assignment
    @Override
    public String uploadAssignment(Long moduleId, MultipartFile file) throws IOException {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Save file locally
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String localFilePath = uploadDir + fileName;
        File destFile = new File(localFilePath);
        file.transferTo(destFile);

        // Map to server URL
        String fileUrl = serverUrl + fileName;

        // Update module assignment path
        module.setAssignmentPath(fileUrl);
        courseModuleRepository.save(module);

        return fileUrl;
    }

    // Updating an assignment for a module
    @Override
    public String updateAssignment(Long moduleId, MultipartFile file) throws IOException {
        // Fetch the CourseModule by ID
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        // Save the new file locally
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String localFilePath = uploadDir + fileName;
        File destFile = new File(localFilePath);
        file.transferTo(destFile);

        // Map the file to the server URL
        String fileUrl = serverUrl + fileName;

        // Update the assignment path
        module.setAssignmentPath(fileUrl);

        // Save the updated module with the new assignment path
        courseModuleRepository.save(module);

        // Return the new assignment URL
        return fileUrl;
    }


    // Fetching assignment URL for a module
    @Override
    public String getAssignment(Long moduleId) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        return module.getAssignmentPath();
    }

    // Deleting an assignment
    @Override
    public void deleteAssignment(Long moduleId) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        module.setAssignmentPath(null);
        courseModuleRepository.save(module);
    }

    // Saving MCQs for a module
    @Override
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

    // Updating an MCQ for a module
    @Override
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
        dto.setAssignmentPath(module.getAssignmentPath());
        return dto;
    }
}
