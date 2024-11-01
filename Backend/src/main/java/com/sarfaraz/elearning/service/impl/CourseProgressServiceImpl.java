package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.constants.ErrorEnum;
import com.sarfaraz.elearning.constants.UserCreatedBy;
import com.sarfaraz.elearning.exceptions.ResourceNotFoundException;
import com.sarfaraz.elearning.model.CourseModule;
import com.sarfaraz.elearning.model.CourseModuleLesson;
import com.sarfaraz.elearning.model.CourseProgress;
import com.sarfaraz.elearning.model.Student;
import com.sarfaraz.elearning.repository.CourseModuleLessonRepository;
import com.sarfaraz.elearning.repository.CourseModuleRepository;
import com.sarfaraz.elearning.repository.CourseProgressRepository;
import com.sarfaraz.elearning.repository.StudentRepository;
import com.sarfaraz.elearning.rest.CourseModuleLessonController;
import com.sarfaraz.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import com.sarfaraz.elearning.service.CourseProgressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class CourseProgressServiceImpl implements CourseProgressService {

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Autowired
    private CourseModuleLessonRepository courseModuleLessonRepository;
    @Autowired
    private CourseModuleLessonController courseModuleLessonController;

    @Transactional
    @Override
    public CourseProgressResponseDTO updateProgress(Long studentId, Long courseModuleLessonId, CourseProgressRequestDTO progressRequest) {
        // Retrieve the student and course module lesson
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
        CourseModuleLesson lesson = courseModuleLessonRepository.findById(courseModuleLessonId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND));

        // Find existing progress
        CourseProgress courseProgress = courseProgressRepository.findByStudentAndLesson(studentId, courseModuleLessonId);

        if (courseProgress == null) {
            // Create new progress if it doesn't exist
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lesson);
            courseProgress.setCreatedBy(UserCreatedBy.Self);
            courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        }

        // Update progress percentage only if the new progress is greater
        if (progressRequest.getProgressPercentage() > courseProgress.getProgressPercentage()) {
            courseProgress.setProgressPercentage(progressRequest.getProgressPercentage());
        }

        // Save the progress
        courseProgressRepository.save(courseProgress);

        return convertToResponseDTO(courseProgress); // Your method to convert the entity to DTO
    }

    @Override
    public CourseProgressResponseDTO getProgress(Long studentId, Long courseModuleLessonId) {
        // Retrieve the progress based on student ID and course module lesson ID
        CourseProgress courseProgress = courseProgressRepository.findByStudent_UserIdAndCourseModuleLesson_ModuleId(studentId, courseModuleLessonId);

        // Return the retrieved course progress as DTO
        return convertToResponseDTO(courseProgress);
    }
    // New method to mark a module as completed
    @Transactional
    @Override
    public CourseProgressResponseDTO markModuleAsCompleted(Long userId, Long moduleId) {
        // Find the student
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));

        // Find the module
        CourseModule module = courseModuleRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.MODULE_NOT_FOUND));

        // Find existing progress for the student and the course
        CourseProgress courseProgress = courseProgressRepository.findByStudent_UserIdAndCourseModuleLesson_ModuleId(userId, module.getCourse().getId());

        if (courseProgress == null) {
            // Create new progress if it doesn't exist
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            module.setCourse(module.getCourse()); // Assuming you have a setCourse method
            courseProgress.setCreatedBy(UserCreatedBy.Self);
            courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            courseProgress.setProgressPercentage(0.0); // Initialize progress percentage
//            courseProgress.setCompletedModules(new HashSet<>()); // Initialize completed modules
        }

        // Check if the module is already marked as completed
        if (courseProgress.getCompletedModules().contains(moduleId)) {
            throw new RuntimeException("Module already marked as completed");
        }

        // Mark module as completed
        courseProgress.getCompletedModules().add(moduleId);

        // Calculate the new progress percentage
        double totalModules = module.getCourseModuleLessons().size(); // Getting the total number of modules
        double completedModules = courseProgress.getCompletedModules().size();
        double newProgressPercentage = (completedModules / totalModules) * 100;
        courseProgress.setProgressPercentage(newProgressPercentage);

        // Update last studied timestamp
        courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // Save updated progress
        courseProgressRepository.save(courseProgress);

        return convertToResponseDTO(courseProgress);
    }


    // Helper method to convert CourseProgress entity to Response DTO
    private CourseProgressResponseDTO convertToResponseDTO(CourseProgress courseProgress) {
        CourseProgressResponseDTO dto = new CourseProgressResponseDTO();
        dto.setId(courseProgress.getId());
        dto.setStudentId(courseProgress.getStudent().getUserId());
        dto.setCourseModuleLessonId(courseProgress.getCourseModuleLesson().getId());
        dto.setProgressPercentage(courseProgress.getProgressPercentage());
        dto.setLastStudiedAt(courseProgress.getCreatedAt().toLocalDateTime());
        return dto;
    }
}
