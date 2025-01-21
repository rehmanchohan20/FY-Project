package com.rehman.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_satisfaction")
public class CustomerSatisfaction extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score_last_month")
    private Double scoreLastMonth;

    @Column(name = "score_this_month")
    private Double scoreThisMonth;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
