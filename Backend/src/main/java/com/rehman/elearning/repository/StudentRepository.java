package com.rehman.elearning.repository;

import com.rehman.elearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Override
    List<Student> findAll();
    Optional<Student> findById(Long userId);

}
