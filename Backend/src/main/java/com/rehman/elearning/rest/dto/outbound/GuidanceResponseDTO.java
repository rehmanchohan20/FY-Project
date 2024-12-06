package com.rehman.elearning.rest.dto.outbound;

import java.util.List;

public class GuidanceResponseDTO {
    private Long id;
    private String question;
    private String answer;
    private Long studentId;
    private List<Long> courseIds;  // List of recommended courses (using their IDs)
    private String nextStepRecommendation;

    // Constructor
    public GuidanceResponseDTO(Long id, String question, String answer, Long studentId, List<Long> courseIds, String nextStepRecommendation) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.studentId = studentId;
        this.courseIds = courseIds;
        this.nextStepRecommendation = nextStepRecommendation;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public List<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public String getNextStepRecommendation() {
        return nextStepRecommendation;
    }

    public void setNextStepRecommendation(String nextStepRecommendation) {
        this.nextStepRecommendation = nextStepRecommendation;
    }
}
