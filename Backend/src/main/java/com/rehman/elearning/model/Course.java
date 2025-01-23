package com.rehman.elearning.model;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.constants.CourseStatusEnum;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
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
    private CourseStatusEnum status;

    @Column (name = "thumbnail")
    private String thumbnail;

    @Column(name = "locked", nullable = false)
    private boolean locked = true; // Default to true, indicating locked

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private CoursePrice coursePrice;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private CourseOffer courseOffer;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseModule> courseModule = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private List<CourseEnrollmentData> enrollments;



    @ManyToMany
    @JoinTable(
            name = "enrolled_Student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Payment> payments;

    @ManyToMany
    @JoinTable(
            name = "course_guidance",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "guidance_id")
    )
    private Set<Guidance> guidances = new HashSet<>();



    public int getEnrolledStudentsCount() {
        return this.students != null ? this.students.size() : 0;
    }


    //Constructors

    // Default constructor
    public Course() {
    }

    // Constructor for deserialization
    public Course(Long id) {
        this.id = id;
    }


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

    public CourseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CourseStatusEnum status) {
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public CourseOffer getCourseOffer() {
        return courseOffer;
    }

    public void setCourseOffer(CourseOffer courseOffer) {
        this.courseOffer = courseOffer;
    }

    public Set<CourseModule> getcourseModule() {
        return courseModule;
    }

    public void setcourseModule(Set<CourseModule> courseModule) {
        this.courseModule = courseModule;
    }

    public void addCourseModule(CourseModule module) {
        this.courseModule.add(module);
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

    public List<CourseEnrollmentData> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<CourseEnrollmentData> enrollments) {
        this.enrollments = enrollments;
    }
}
