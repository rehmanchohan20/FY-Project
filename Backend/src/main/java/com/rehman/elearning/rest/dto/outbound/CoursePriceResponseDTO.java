package com.rehman.elearning.rest.dto.outbound;

public class CoursePriceResponseDTO {
    private Double price;
    private String currency;

    public CoursePriceResponseDTO(Double price, String currency) {
        this.price = price;
        this.currency = currency;
    }

    public CoursePriceResponseDTO() {
    }

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
