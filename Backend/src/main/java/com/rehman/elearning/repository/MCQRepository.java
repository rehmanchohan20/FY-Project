package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseModule;
import com.rehman.elearning.model.MCQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCQRepository extends JpaRepository<MCQ, Long> {
    List<MCQ> findAllByCourseModule(CourseModule courseModule);
}
