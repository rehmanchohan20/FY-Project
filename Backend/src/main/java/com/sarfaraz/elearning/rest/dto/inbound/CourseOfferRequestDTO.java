package com.sarfaraz.elearning.rest.dto.inbound;

public class CourseOfferRequestDTO {
    private String offerTitle;
    private Double discount;


    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
