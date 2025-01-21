package com.rehman.elearning.repository;

import com.rehman.elearning.model.CustomerSatisfaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;


@Repository
public interface CustomerSatisfactionRepository extends JpaRepository<CustomerSatisfaction, Long> {

    @Query("SELECT AVG(cs.scoreLastMonth) FROM CustomerSatisfaction cs WHERE cs.createdAt >= :lastMonth")
    Double getLastMonthScore(@Param("lastMonth") Timestamp lastMonth);

    @Query("SELECT AVG(cs.scoreLastMonth) FROM CustomerSatisfaction cs WHERE MONTH(cs.createdAt) = MONTH(CURRENT_DATE)")
    Double getThisMonthScore();

}
