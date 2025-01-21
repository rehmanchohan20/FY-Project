package com.rehman.elearning.rest.dto.outbound;

public class CustomerSatisfactionResponseDTO {

    private Double scoreLastMonth;
    private Double scoreThisMonth;

    // Getters and Setters
    public Double getScoreLastMonth() {
        return scoreLastMonth;
    }

    public void setScoreLastMonth(Double scoreLastMonth) {
        this.scoreLastMonth = scoreLastMonth;
    }

    public Double getScoreThisMonth() {
        return scoreThisMonth;
    }

    public void setScoreThisMonth(Double scoreThisMonth) {
        this.scoreThisMonth = scoreThisMonth;
    }
}
