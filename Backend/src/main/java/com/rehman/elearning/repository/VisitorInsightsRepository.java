package com.rehman.elearning.repository;

import com.rehman.elearning.model.VisitorInsights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorInsightsRepository extends JpaRepository<VisitorInsights, Long> {
}
