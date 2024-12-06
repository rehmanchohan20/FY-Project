package com.rehman.elearning.rest.dto.inbound;

import com.rehman.elearning.model.Student;

public class GuidanceRequestDTO {
    private String question;
    private Student student;

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
