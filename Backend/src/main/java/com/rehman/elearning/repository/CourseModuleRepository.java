package com.rehman.elearning.repository;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    // findByCourseId
    List<CourseModule> findByCourseId(Long courseId);
    boolean existsByCourseAndPriority(Course course, int priority);


}
