package com.rehman.elearning.repository;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{



    List<Course> findByStudents_UserId(Long studentId);

    List<Course> findByTitleContaining(String title);
    @Query("SELECT c FROM Course c WHERE c.title LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Course> searchCoursesByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Course c WHERE c.teacher.userId = :teacherId")
    List<Course> findCoursesByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT c FROM Course c WHERE :student NOT MEMBER OF c.students")
    List<Course> findByStudentsNotContaining(@Param("student") Student student);

    List<Course> findByCategory(CategoryEnum category);

    // Custom method to find recommended courses
    @Query("SELECT c FROM Course c WHERE c.category IN :categories AND c.students IS EMPTY")
    List<Course> findRecommendedCourses(@Param("categories") List<CategoryEnum> categories);

    List<Course> findByIdIn(Set<Long> courseIds);

    @Query("SELECT COUNT(c) FROM Course c")
    long count();

    @Query("SELECT c FROM Course c JOIN c.students s GROUP BY c.id ORDER BY COUNT(s) DESC")
    List<Course> findTopCoursesByEnrollments();

}