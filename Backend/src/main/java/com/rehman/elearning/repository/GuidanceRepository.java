package com.rehman.elearning.repository;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Guidance;
import com.rehman.elearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuidanceRepository extends JpaRepository<Guidance, Long> {

    // Custom query to find guidance by student or course, if needed
    List<Guidance> findByStudent(Student student);

    // Another example: Find guidance by course
    List<Guidance> findByCoursesContaining(Course course);
}
