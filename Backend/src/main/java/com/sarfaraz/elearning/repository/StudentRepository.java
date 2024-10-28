package com.sarfaraz.elearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<com.sarfaraz.elearning.model.Student, Long> {
}
