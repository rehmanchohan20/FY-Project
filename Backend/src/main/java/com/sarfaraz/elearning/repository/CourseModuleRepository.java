package com.sarfaraz.elearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseModuleRepository extends JpaRepository<com.sarfaraz.elearning.model.CourseModule, Integer> {
}
