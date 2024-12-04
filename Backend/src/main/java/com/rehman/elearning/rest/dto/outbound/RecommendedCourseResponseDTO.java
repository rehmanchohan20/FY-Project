package com.rehman.elearning.rest.dto.outbound;

import com.rehman.elearning.constants.CategoryEnum;

public class RecommendedCourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String thumbnail;
    private String teacherName;
    private String price;
    private String currency;
    private CategoryEnum category;

    public RecommendedCourseResponseDTO(Long id, String title, String description, String thumbnail, String teacherName, String price, String currency,CategoryEnum category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.teacherName = teacherName;
        this.price = price;
        this.currency = currency;
        this.category=category;
    }

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}
