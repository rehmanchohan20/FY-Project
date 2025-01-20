package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lesson);
            courseProgress.setCreatedBy(UserCreatedBy.Self);
            courseProgress.setProgressPercentage(0.0);
        }

        if (progressRequest.getProgressPercentage() > courseProgress.getProgressPercentage()) {
            courseProgress.setProgressPercentage(progressRequest.getProgressPercentage());
        }

        courseProgress.getCompletedLessons().add(courseModuleLessonId);
        courseProgress.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        courseProgressRepository.save(courseProgress);

        return convertToResponseDTO(courseProgress);
    }

    @Override
    public CourseProgressResponseDTO getProgress(Long studentId, Long courseModuleLessonId) {
        CourseProgress courseProgress = courseProgressRepository.findByStudentAndLesson(studentId, courseModuleLessonId);
        if (courseProgress == null) {
            CourseModuleLesson lesson = courseModuleLessonRepository.findById(courseModuleLessonId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND));
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lesson);
            courseProgress.setCreatedBy(UserCreatedBy.Self);
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

        // Use a Set instead of List
        Set<CourseModuleLesson> lessons = module.getCourseModuleLessons();
        if (lessons.isEmpty()) {
            throw new ResourceNotFoundException(ErrorEnum.LESSON_NOT_FOUND);
        }

        CourseProgress courseProgress = courseProgressRepository.findByStudent_UserIdAndCourseModuleLesson_ModuleId(userId, module.getCourse().getId());
        if (courseProgress == null) {
            courseProgress = new CourseProgress();
            courseProgress.setStudent(student);
            courseProgress.setCourseModuleLesson(lessons.iterator().next());
            courseProgress.setCreatedBy(UserCreatedBy.Self);
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
        courseProgress.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        courseProgressRepository.save(courseProgress);

        return convertToResponseDTO(courseProgress);
    }

    @Transactional
    @Override
    public CourseProgressResponseDTO getOverallCourseProgress(Long userId, Long courseId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
        List<CourseModule> modules = courseModuleRepository.findByCourseId(courseId);

        int totalLessons = 0;
        int completedLessons = 0;

        for (CourseModule module : modules) {
            Set<CourseModuleLesson> lessons = module.getCourseModuleLessons();
            totalLessons += lessons.size();

            for (CourseModuleLesson lesson : lessons) {
                CourseProgress progress = courseProgressRepository.findByStudentAndLesson(userId, lesson.getId());
                // Check if the lesson is completed
                if (progress != null && progress.getProgressPercentage() == 100.0) {
                    completedLessons++;
                }
            }
        }

        double overallProgress = totalLessons > 0 ? (completedLessons / (double) totalLessons) * 100 : 0.0;

        // Create a temporary CourseProgress entity to reuse the convertToResponseDTO method
        CourseProgress tempCourseProgress = new CourseProgress();
        tempCourseProgress.setId(courseId);
        tempCourseProgress.setStudent(student);
        tempCourseProgress.setCourseModuleLesson(modules.get(0).getCourseModuleLessons().iterator().next());
        tempCourseProgress.setProgressPercentage(overallProgress);
        tempCourseProgress.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return convertToResponseDTO(tempCourseProgress);
    }

    @Transactional
    @Override
    public List<CourseProgressResponseDTO> getAllCoursesProgress(Long userId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
        Set<Course> enrolledCourses = student.getCourses();

        List<CourseProgressResponseDTO> progressList = new ArrayList<>();

        for (Course course : enrolledCourses) {
            List<CourseModule> modules = courseModuleRepository.findByCourseId(course.getId());

            int totalLessons = 0;
            int completedLessons = 0;

            for (CourseModule module : modules) {
                Set<CourseModuleLesson> lessons = module.getCourseModuleLessons();
                totalLessons += lessons.size();

                for (CourseModuleLesson lesson : lessons) {
                    CourseProgress progress = courseProgressRepository.findByStudentAndLesson(userId, lesson.getId());
                    // Check if the lesson is completed
                    if (progress != null && progress.getProgressPercentage() == 100.0) {
                        completedLessons++;
                    }
                }
            }

            double overallProgress = totalLessons > 0 ? (completedLessons / (double) totalLessons) * 100 : 0.0;

            // Create a temporary CourseProgress entity to reuse the convertToResponseDTO method
            CourseProgress tempCourseProgress = new CourseProgress();
            tempCourseProgress.setId(course.getId());
            tempCourseProgress.setStudent(student);
            tempCourseProgress.setCourseModuleLesson(modules.get(0).getCourseModuleLessons().iterator().next());
            tempCourseProgress.setProgressPercentage(overallProgress);
            tempCourseProgress.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            progressList.add(convertToResponseDTO(tempCourseProgress));
        }

        return progressList;
    }

    private CourseProgressResponseDTO convertToResponseDTO(CourseProgress courseProgress) {
        CourseProgressResponseDTO dto = new CourseProgressResponseDTO();
        dto.setId(courseProgress.getId());
        dto.setStudentId(courseProgress.getStudent().getUserId());
        dto.setCourseModuleLessonId(courseProgress.getCourseModuleLesson().getId());
        dto.setProgressPercentage(courseProgress.getProgressPercentage());
        dto.setLastStudiedAt(courseProgress.getUpdatedAt());
        return dto;
    }
}