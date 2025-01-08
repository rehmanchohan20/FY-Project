package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.model.CourseModuleLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseModuleLessonRepository extends JpaRepository<CourseModuleLesson, Long> {

    List<CourseModuleLesson> findByCourseModule_Id(Long moduleId);
    void deleteAllByCourseModule(CourseModule module);
}
