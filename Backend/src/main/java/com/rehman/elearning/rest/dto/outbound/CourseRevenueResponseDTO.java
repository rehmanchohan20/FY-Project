package com.rehman.elearning.rest.dto.outbound;

public class CourseRevenueResponseDTO {
    private String day;
    private int sales;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
