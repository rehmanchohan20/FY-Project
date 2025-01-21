package com.rehman.elearning.rest.dto.outbound;

public class SalesSummaryResponseDTO {

    private Double totalSales;
    private Long totalCourses;
    private Long todaysJoining;
    private Long teachersOnboard;

    // Getters and Setters
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
