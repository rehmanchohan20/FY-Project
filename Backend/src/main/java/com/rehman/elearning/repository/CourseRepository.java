package com.rehman.elearning.repository;

import com.rehman.elearning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    List<Course> findByTitleContaining(String title);
    @Query("SELECT c FROM Course c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Course> searchCoursesByKeyword(@Param("keyword") String keyword);

}