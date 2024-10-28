package com.sarfaraz.elearning.model;

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


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "courseModule", fetch = FetchType.LAZY)
    private Set<CourseModuleLesson> courseModuleLessons;


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
}
