package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.repository.CourseModuleRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.rest.dto.inbound.CourseModuleRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseModuleResponseDTO;
import com.rehman.elearning.service.CourseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseModuleServiceImpl implements CourseModuleService {

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    @Override
    public List<CourseModuleResponseDTO> addModules(Long courseId, List<CourseModuleRequestDTO> requests) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));

        List<CourseModuleResponseDTO> responseDTOs = requests.stream()
                .map(request -> {
                    CourseModule module = new CourseModule();
                    module.setHeading(request.getHeading());
                    module.setDescription(request.getDescription());
                    module.setPriority(request.getPriority());
                    module.setCourse(course);
                    module.setCreatedBy(UserCreatedBy.Teacher);

                    module = courseModuleRepository.save(module);
                    return convertToResponseDTO(module);
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }


    @Override
    public List<CourseModuleResponseDTO> getAllModules(Long courseId) {
        List<CourseModule> modules = courseModuleRepository.findByCourseId(courseId);
        return modules.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseModuleResponseDTO updateModule(Long moduleId, CourseModuleRequestDTO request) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.RESOURCE_NOT_FOUND));
        module.setHeading(request.getHeading());
        module.setDescription(request.getDescription());
        module.setPriority(request.getPriority());
        module.setCreatedBy(UserCreatedBy.Teacher);
        module = courseModuleRepository.save(module);
        return convertToResponseDTO(module);
    }

    @Override
    public void deleteModule(Long moduleId) {
        courseModuleRepository.deleteById(moduleId);
    }

    private CourseModuleResponseDTO convertToResponseDTO(CourseModule module) {
        CourseModuleResponseDTO dto = new CourseModuleResponseDTO();
        dto.setId(module.getId());
        dto.setHeading(module.getHeading());
        dto.setDescription(module.getDescription());
        dto.setPriority(module.getPriority());
        return dto;
    }
}

