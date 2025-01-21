package com.rehman.elearning.rest.dto.outbound;


public class VisitorInsightsResponseDTO {

    private String month;
    private Long visitors;

    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getVisitors() {
        return visitors;
    }

    public void setVisitors(Long visitors) {
        this.visitors = visitors;
    }
}
