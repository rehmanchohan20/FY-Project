package com.rehman.elearning.rest.dto.outbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)  // Exclude null fields in JSON response
public class GuidanceResponse {
    private Long guidanceId;
    private String answer;
    private String nextStepRecommendation;
    private List<Map<String, Object>> courses; // List of Maps with courseId and courseTitle
    private String whatsappLink;

    // Constructor for full response
    public GuidanceResponse(Long guidanceId, String answer, String nextStepRecommendation,
                            List<Map<String, Object>> courses, String whatsappLink) {
        this.guidanceId = guidanceId;
        this.answer = answer;
        this.nextStepRecommendation = nextStepRecommendation;
        this.courses = courses;
        this.whatsappLink = whatsappLink;
    }

    // Constructor for only WhatsApp link response
    public GuidanceResponse(String whatsappLink) {
        this.whatsappLink = whatsappLink;
    }

    // Getters and setters
    public Long getGuidanceId() {
        return guidanceId;
    }

    public void setGuidanceId(Long guidanceId) {
        this.guidanceId = guidanceId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNextStepRecommendation() {
        return nextStepRecommendation;
    }

    public void setNextStepRecommendation(String nextStepRecommendation) {
        this.nextStepRecommendation = nextStepRecommendation;
    }

    public List<Map<String, Object>> getCourses() {
        return courses;
    }

    public void setCourses(List<Map<String, Object>> courses) {
        this.courses = courses;
    }

    public String getWhatsappLink() {
        return whatsappLink;
    }

    public void setWhatsappLink(String whatsappLink) {
        this.whatsappLink = whatsappLink;
    }
}
