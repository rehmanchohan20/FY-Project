package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRevenueRepository extends JpaRepository<CourseRevenue, Long> {
}
