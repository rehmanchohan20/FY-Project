package com.rehman.elearning.rest.dto.inbound;

import java.util.List;

public class MCQRequestDTO {
    private String question;
    private List<String> options;
    private String correctAnswer;

    // Getters and Setters


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

