package com.rehman.elearning.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "mcq")
public class MCQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @ElementCollection
    @Column(name = "options")
    private List<String> options;

    @Column(name = "correct_answer")
    private String correctAnswer;

    // Many-to-One relation with CourseModule
    @ManyToOne
    @JoinColumn(name = "course_module_id", nullable = false)
    private CourseModule courseModule;

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

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }
}
