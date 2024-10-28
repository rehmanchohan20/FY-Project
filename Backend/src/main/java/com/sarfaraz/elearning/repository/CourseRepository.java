package com.sarfaraz.elearning.repository;

import com.sarfaraz.elearning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<com.sarfaraz.elearning.model.Course, Long>{
    List<Course> findByTitleContaining(String title);
}