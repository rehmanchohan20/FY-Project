package com.rehman.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sales_summary")
public class SalesSummary extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_sales")
    private Double totalSales;

    @Column(name = "total_courses")
    private Long totalCourses;

    @Column(name = "todays_joining")
    private Long todaysJoining;

    @Column(name = "teachers_onboard")
    private Long teachersOnboard;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Long getTodaysJoining() {
        return todaysJoining;
    }

    public void setTodaysJoining(Long todaysJoining) {
        this.todaysJoining = todaysJoining;
    }

    public Long getTeachersOnboard() {
        return teachersOnboard;
    }

    public void setTeachersOnboard(Long teachersOnboard) {
        this.teachersOnboard = teachersOnboard;
    }
}
