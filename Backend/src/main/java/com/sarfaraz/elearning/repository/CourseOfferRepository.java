package com.sarfaraz.elearning.repository;

import com.sarfaraz.elearning.model.CourseOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseOfferRepository extends JpaRepository<com.sarfaraz.elearning.model.CourseOffer, Long> {
    // findByCourseId
    List<CourseOffer> findByCourseId(Long courseId);

}
