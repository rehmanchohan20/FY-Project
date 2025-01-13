package com.rehman.elearning.rest.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CoursePriceRequestDTO {

    @NotNull(message = "Price must not be null")
    private Double price; // Changed to Double for precision

    @NotBlank(message = "Currency must not be blank") // Optional: Add validation for currency
    private String currency;

    public CoursePriceRequestDTO(Double amount, String currency) {
        this.price = amount;
        this.currency = currency;
    }

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
