package com.sarfaraz.elearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePriceRepository extends JpaRepository<com.sarfaraz.elearning.model.CoursePrice, Integer> {
}
