package com.rehman.elearning.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course_progress")
public class CourseProgress extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_module_lesson_id")
    private CourseModuleLesson courseModuleLesson;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;


    @Column(name = "progress_percentage")
    private double progressPercentage; // Changed from String to double to represent percentage

    @ElementCollection
    private List<Long> completedModules = new ArrayList<>(); // Stores completed module IDs


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseModuleLesson getCourseModuleLesson() {
        return courseModuleLesson;
    }

    public void setCourseModuleLesson(CourseModuleLesson courseModuleLesson) {
        this.courseModuleLesson = courseModuleLesson;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public List<Long> getCompletedModules() {
        return completedModules;
    }

    public void setCompletedModules(List<Long> completedModules) {
        this.completedModules = completedModules;
    }
}
