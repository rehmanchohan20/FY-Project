package com.rehman.elearning.repository;

import com.rehman.elearning.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Custom method to find tickets by student user ID
    List<Ticket> findByStudent_UserId(Long userId);

    // Custom method to find tickets with a specific status
    List<Ticket> findByStatus(String status);

    // Custom method to find resolved tickets
    List<Ticket> findByResolvedAtNotNull();

    // Custom method to find open tickets
    List<Ticket> findByResolvedAtIsNull();

    // Custom method to find tickets created after a certain date
    List<Ticket> findByCreatedAtAfter(LocalDateTime createdAt);
}
