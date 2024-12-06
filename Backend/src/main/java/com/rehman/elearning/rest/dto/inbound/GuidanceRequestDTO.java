package com.rehman.elearning.rest.dto.inbound;

import com.rehman.elearning.model.Course;

import java.util.List;

public class GuidanceRequestDTO {

    private Long studentId;  // Only pass the studentId
    private String question;
    private List<Course> courses; // Courses related to the question

    // Getters and Setters

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
