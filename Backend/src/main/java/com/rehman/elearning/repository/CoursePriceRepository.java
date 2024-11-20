package com.rehman.elearning.repository;

import com.rehman.elearning.model.CoursePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursePriceRepository extends JpaRepository<CoursePrice, Long> {
}
