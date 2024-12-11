package com.rehman.elearning.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guidance extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private String question;
    private String answer;
    private String keywords;

    @ManyToMany
    @JoinTable(
            name = "course_guidance",
            joinColumns = @JoinColumn(name = "guidance_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();  // Courses related to the guidance answer

    // Additional fields to personalize guidance for the student (can be updated later)
    private String nextStepRecommendation;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getNextStepRecommendation() {
        return nextStepRecommendation;
    }

    public void setNextStepRecommendation(String nextStepRecommendation) {
        this.nextStepRecommendation = nextStepRecommendation;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
