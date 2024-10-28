package com.sarfaraz.elearning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "course_progress")
public class CourseProgress extends  CommonEntity {
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

    @Column(name = "progress")
    private String progress;

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

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
