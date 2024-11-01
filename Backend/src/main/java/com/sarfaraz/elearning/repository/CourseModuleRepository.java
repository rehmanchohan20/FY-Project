package com.sarfaraz.elearning.repository;

import com.sarfaraz.elearning.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseModuleRepository extends JpaRepository<com.sarfaraz.elearning.model.CourseModule, Long> {
    // findByCourseId
    List<CourseModule> findByCourseId(Long courseId);

}
