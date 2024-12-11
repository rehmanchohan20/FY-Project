package com.rehman.elearning.repository;

import com.rehman.elearning.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
//    List<Payment> findAllByCourseId(Long courseId);
//    // If you're expecting a single payment by courseId, use Optional
//    List<Payment> findByCourseId(Long courseId);
//    // If you're expecting a single payment by transactionId, use Optional
//    Optional<Payment> findByTransactionId(String transactionId);


}
