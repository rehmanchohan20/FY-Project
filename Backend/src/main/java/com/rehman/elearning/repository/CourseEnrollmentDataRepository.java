package com.rehman.elearning.repository;

import com.rehman.elearning.model.CourseEnrollmentData;
import com.rehman.elearning.rest.dto.inbound.TeacherDashboardDTO;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CourseEnrollmentDataRepository extends JpaRepository<CourseEnrollmentData, Long> {
//    @Query("SELECT SUM(c.enrollmentCount) FROM CourseEnrollmentData c WHERE c.course.teacher.userId = :teacherId")
//    Long countTotalEnrollmentsByTeacherId(Long teacherId);



    @Query("SELECT COUNT(s) FROM Course c " +
            "JOIN c.students s " +
            "WHERE c.teacher.userId = :teacherId")
    Long countTotalStudentsEnrolledByTeacher(@Param("teacherId") Long teacherId);


//    @Query("SELECT COUNT(c) FROM CourseEnrollmentData c WHERE c.course.teacher.userId = :teacherId AND c.createdAt >= :date")
//    Long countDailyEnrollmentsByTeacherId(Long teacherId, LocalDate date);


    @Query("SELECT COUNT(s) FROM Student s " +
            "JOIN s.courses c " +  // Join the students to the courses
            "WHERE c.teacher.userId = :teacherId " +  // Filter courses by teacher ID
            "AND EXISTS (SELECT 1 FROM c.students enrolled " +  // Ensure the student is enrolled in the course
            "WHERE enrolled = :date)")  // Assuming 'enrollmentDate' is tracked in the join table
    Long countStudentsEnrolledTodayByTeacher(@Param("teacherId") Long teacherId,
                                             @Param("date") LocalDate date);





    @Query("SELECT c FROM CourseEnrollmentData c WHERE c.course.teacher.userId = :teacherId")
    List<CourseEnrollmentData> findByCourseTeacherUserId(@Param("teacherId") Long teacherId);
}