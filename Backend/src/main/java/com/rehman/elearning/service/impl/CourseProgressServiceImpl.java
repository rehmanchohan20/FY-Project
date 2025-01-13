package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.model.CourseModuleLesson;
import com.rehman.elearning.model.CourseProgress;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.CourseModuleLessonRepository;
import com.rehman.elearning.repository.CourseModuleRepository;
import com.rehman.elearning.repository.CourseProgressRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.CourseProgressRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseProgressResponseDTO;
import com.rehman.elearning.service.CourseProgressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    @Transactional
    @Override
    public CourseProgressResponseDTO updateProgress(Long studentId, Long courseModuleLessonId, CourseProgressRequestDTO progressRequest) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
        CourseModuleLesson lesson = courseModuleLessonRepository.findById(courseModuleLessonId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND));

        CourseProgress courseProgress = courseProgressRepository.findByStudentAndLesson(studentId, courseModuleLessonId);
        if (courseProgress == null) {
            // Initialize progress to 0% if it does not exist
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lesson);
            courseProgress.setCreatedBy(UserCreatedBy.Self);
            courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            courseProgress.setProgressPercentage(0.0);
        }

        // Update progress if the new percentage is greater
        if (progressRequest.getProgressPercentage() > courseProgress.getProgressPercentage()) {
            courseProgress.setProgressPercentage(progressRequest.getProgressPercentage());
        }

        courseProgressRepository.save(courseProgress);

        return convertToResponseDTO(courseProgress);
    }

    @Override
    public CourseProgressResponseDTO getProgress(Long studentId, Long courseModuleLessonId) {
        CourseProgress courseProgress = courseProgressRepository.findByStudent_UserIdAndCourseModuleLesson_ModuleId(studentId, courseModuleLessonId);
        if (courseProgress == null) {
            // Initialize progress at 0% if it doesn't exist
            CourseModuleLesson lesson = courseModuleLessonRepository.findById(courseModuleLessonId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND));
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lesson);
            courseProgress.setCreatedBy(UserCreatedBy.Self);
            courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            courseProgress.setProgressPercentage(0.0);
            courseProgressRepository.save(courseProgress);
        }
        return convertToResponseDTO(courseProgress);
    }

    @Transactional
    @Override
    public CourseProgressResponseDTO markModuleAsCompleted(Long userId, Long moduleId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.MODULE_NOT_FOUND));

        // Ensure there are lessons in the module
        List<CourseModuleLesson> lessons = (List<CourseModuleLesson>) module.getCourseModuleLessons();
        if (lessons.isEmpty()) {
            throw new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND);
        }

        CourseProgress courseProgress = courseProgressRepository.findByStudent_UserIdAndCourseModuleLesson_ModuleId(userId, module.getCourse().getId());
        if (courseProgress == null) {
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lessons.get(0)); // Safe to get the first lesson
            courseProgress.setCreatedBy(UserCreatedBy.Self);
            courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            courseProgress.setProgressPercentage(0.0);
        }

        if (courseProgress.getCompletedModules().contains(moduleId)) {
            throw new RuntimeException("Module already marked as completed");
        }

        courseProgress.getCompletedModules().add(moduleId);

        double totalModules = lessons.size();
        double completedModules = courseProgress.getCompletedModules().size();
        double newProgressPercentage = (completedModules / totalModules) * 100;
        courseProgress.setProgressPercentage(newProgressPercentage);

        courseProgress.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        courseProgressRepository.save(courseProgress);

        return convertToResponseDTO(courseProgress);
    }

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