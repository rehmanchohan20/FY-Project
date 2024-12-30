package com.rehman.elearning.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "course_module")
public class CourseModule extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "heading")
    private String heading;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private Integer priority;


    // Relationship with Course entity
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    // Relationship with CourseModuleLesson (one-to-many)
    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CourseModuleLesson> courseModuleLessons;

    // Relationship with MCQ (one-to-many)
    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<MCQ> mcqs;

    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Assignment> assignments;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<CourseModuleLesson> getCourseModuleLessons() {
        return courseModuleLessons;
    }

    public void setCourseModuleLessons(Set<CourseModuleLesson> courseModuleLessons) {
        this.courseModuleLessons = courseModuleLessons;
    }

    public Set<MCQ> getMcqs() {
        return mcqs;
    }

    public void setMcqs(Set<MCQ> mcqs) {
        this.mcqs = mcqs;
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }
}
