package com.rehman.elearning.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "descriptions")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "locked", nullable = false)
    private boolean locked = true; // Default to true, indicating locked

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CoursePrice coursePrice;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CourseOffer courseOffer;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CourseModule> courseDetails = new HashSet<>(); // Initialize to avoid NullPointerException

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Student> students;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Payment> payments;

    // New property for the ManyToMany relationship with Guidance
    @ManyToMany
    @JoinTable(
            name = "course_guidance",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "guidance_id")
    )
    private Set<Guidance> guidances = new HashSet<>();

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public CoursePrice getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePrice coursePrice) {
        this.coursePrice = coursePrice;
    }

    public CourseOffer getCourseOffer() {
        return courseOffer;
    }

    public void setCourseOffer(CourseOffer courseOffer) {
        this.courseOffer = courseOffer;
    }

    public Set<CourseModule> getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(Set<CourseModule> courseDetails) {
        this.courseDetails = courseDetails;
    }

    public void addCourseModule(CourseModule module) {
        this.courseDetails.add(module);
        module.setCourse(this);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Guidance> getGuidances() {
        return guidances;
    }

    public void setGuidances(Set<Guidance> guidances) {
        this.guidances = guidances;
    }
}
