package com.rehman.elearning.repository;

import com.rehman.elearning.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT COUNT(t) FROM Teacher t WHERE DATE(t.createdAt) = CURRENT_DATE")
    Long countByJoinDateToday();

    @Query("SELECT COUNT(t) FROM Teacher t")
    long count();

}
