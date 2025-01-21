package com.rehman.elearning.repository;

import com.rehman.elearning.model.TopCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopCoursesRepository extends JpaRepository<TopCourses, Long> {
}
