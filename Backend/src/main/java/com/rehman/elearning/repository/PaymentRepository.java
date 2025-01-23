package com.rehman.elearning.repository;

import com.rehman.elearning.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Custom method to find a payment by its transaction ID
    Optional<Payment> findByTransactionId(String transactionId);
    Optional<Payment> findByStudentUserIdAndCourseId(Long studentId, Long courseId);

    // Admin methods
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.createdAt >= :startDate AND p.createdAt <= :endDate")
    Double calculateWeeklyRevenue(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Query("SELECT SUM(p.amount) FROM Payment p")
    Double calculateTotalSales();

    @Query("SELECT COUNT(DISTINCT p.id) FROM Payment p WHERE MONTH(p.createdAt) = MONTH(CURRENT_DATE) AND YEAR(p.createdAt) = YEAR(CURRENT_DATE)")
    Long countVisitorsThisMonth();

    public List<Payment> findByCourse_Teacher_UserId(Long teacherId);


    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.course.teacher.userId = :teacherId")
    Double calculateTotalRevenueByTeacherId(Long teacherId);

}