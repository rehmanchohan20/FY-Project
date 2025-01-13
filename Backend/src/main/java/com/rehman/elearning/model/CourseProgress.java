package com.rehman.elearning.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private double progressPercentage;

    @ElementCollection
    private Set<Long> completedModules = new HashSet<>();

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

    public Set<Long> getCompletedModules() {
        return completedModules;
    }

    public void setCompletedModules(Set<Long> completedModules) {
        this.completedModules = completedModules;
    }
}