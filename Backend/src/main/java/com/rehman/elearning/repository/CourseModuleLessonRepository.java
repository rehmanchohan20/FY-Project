package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseModuleLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseModuleLessonRepository extends JpaRepository<CourseModuleLesson, Long> {

    List<CourseModuleLesson> findByModuleId(Long moduleId);
}
