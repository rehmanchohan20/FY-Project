package com.rehman.elearning.repository;

import com.rehman.elearning.model.SalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesSummaryRepository extends JpaRepository<SalesSummary, Long> {
}
