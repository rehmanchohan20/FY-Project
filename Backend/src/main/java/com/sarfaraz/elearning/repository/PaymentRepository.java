package com.sarfaraz.elearning.repository;

import com.sarfaraz.elearning.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<com.sarfaraz.elearning.model.Payment, Long> {
    List<Payment> findAllByCourseId(Long courseId);
}
