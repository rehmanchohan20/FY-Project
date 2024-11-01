package com.sarfaraz.elearning.repository;

import com.sarfaraz.elearning.model.CourseModuleLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseModuleLessonRepository extends JpaRepository<com.sarfaraz.elearning.model.CourseModuleLesson, Long> {

    List<CourseModuleLesson> findByModuleId(Long moduleId);
}
