package com.rehman.elearning.rest.dto.outbound;

public class RevenueResponseDTO {

    private Double revenue;
    private String date;

    // Getters and Setters
    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
