package com.sarfaraz.elearning.rest.dto.inbound;

import jakarta.validation.constraints.NotNull;

public class CoursePriceRequestDTO {
    @NotNull(message = "Price must not be null")
    private Double price;// Use appropriate type for price
    private String currency;

    // Getters and Setters
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
