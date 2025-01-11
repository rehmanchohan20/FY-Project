package com.rehman.elearning.repository;

import com.rehman.elearning.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Custom method to find a payment by its transaction ID
    Optional<Payment> findByTransactionId(String transactionId);
    Optional<Payment> findByStudentUserIdAndCourseId(Long studentId, Long courseId);
}