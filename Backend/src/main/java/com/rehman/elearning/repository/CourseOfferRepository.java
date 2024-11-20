package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfferRepository extends JpaRepository<CourseOffer, Long> {
    // findByCourseId
    List<CourseOffer> findByCourseId(Long courseId);

}
