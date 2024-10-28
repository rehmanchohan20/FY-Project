package com.sarfaraz.elearning.rest.dto.outbound;

public class CourseOfferResponseDTO {

    private Long id;
    private Double discount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
