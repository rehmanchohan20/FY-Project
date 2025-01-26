package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseProgress;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {

    @Query("SELECT cp FROM CourseProgress cp WHERE cp.student.userId = :studentId AND cp.courseModuleLesson.moduleId = :lessonId")
    CourseProgress findByStudentAndLesson(@Param("studentId") Long studentId, @Param("lessonId") Long lessonId);

    // Ensure only the latest progress is fetched
    @Query("SELECT cp FROM CourseProgress cp " +
            "WHERE cp.student.userId = :studentId " +
            "AND cp.courseModuleLesson.courseModule.course.id = :courseId " +
            "ORDER BY cp.updatedAt DESC")
    List<CourseProgress> findLatestProgressByStudent_UserIdAndCourseModuleLesson_CourseModule_Course_Id(Long studentId, Long courseId);

    // In case you want to find by moduleId
    CourseProgress findByStudent_UserIdAndCourseModuleLesson_ModuleId(Long userId, Long moduleId);
}
