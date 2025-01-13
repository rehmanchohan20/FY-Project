package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
    @Query("SELECT cp FROM CourseProgress cp WHERE cp.student.userId = :studentId AND cp.courseModuleLesson.moduleId = :lessonId")
    CourseProgress findByStudentAndLesson(@Param("studentId") Long studentId, @Param("lessonId") Long lessonId);

    CourseProgress findByStudent_UserIdAndCourseModuleLesson_ModuleId(Long userId, Long moduleId);
}