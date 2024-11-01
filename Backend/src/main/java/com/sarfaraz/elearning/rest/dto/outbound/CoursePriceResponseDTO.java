package com.sarfaraz.elearning.rest.dto.outbound;

public class CoursePriceResponseDTO {
    private Double price;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
