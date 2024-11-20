package com.rehman.elearning.rest.dto.outbound;

import java.util.List;

public class GuidanceResponseDTO {
    private List<CourseRecommendation> courses;

    public GuidanceResponseDTO(List<CourseRecommendation> courses) {
        this.courses = courses;
    }

    public List<CourseRecommendation> getCourses() {
        return courses;
    }

    public static class CourseRecommendation {
        private String name;
        private String description;

        public CourseRecommendation(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}

