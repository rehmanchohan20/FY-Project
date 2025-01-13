package com.rehman.elearning.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Amount extends CommonEntity {
    @Column(name = "amount")
    private Double amount;

    @Column(name = "currency")
    private String currency;

    // Default constructor
    public Amount() {
    }

    // All-argument constructor
    public Amount(Double amount, String currency) {
        this.amount =  amount;
        this.currency = currency;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
