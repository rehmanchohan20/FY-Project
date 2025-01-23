package com.rehman.elearning.model;


import jakarta.persistence.*;

@Entity
@Table(name = "teacher_course_statistics")
public class TeacherCourseStatistics extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Column(name = "total_courses")
    private Long totalCourses;

    @Column(name = "total_enrollments")
    private Long totalEnrollments;

    @Column(name = "daily_enrollments")
    private Long dailyEnrollments;

    @Column(name = "total_revenue")
    private Double totalRevenue;

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Long getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(Long totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }

    public Long getDailyEnrollments() {
        return dailyEnrollments;
    }

    public void setDailyEnrollments(Long dailyEnrollments) {
        this.dailyEnrollments = dailyEnrollments;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
