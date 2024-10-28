package com.sarfaraz.elearning.rest.dto.outbound;

import java.util.Set;

public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private TeacherResponseDTO teacher;
    private CoursePriceResponseDTO coursePrice;
    private CourseOfferResponseDTO courseOffer;
    private Set<CourseModuleResponseDTO> courseModules;
    private Set<StudentResponseDTO> students;

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

    public TeacherResponseDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherResponseDTO teacher) {
        this.teacher = teacher;
    }

    public CoursePriceResponseDTO getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(CoursePriceResponseDTO coursePrice) {
        this.coursePrice = coursePrice;
    }

    public CourseOfferResponseDTO getCourseOffer() {
        return courseOffer;
    }

    public void setCourseOffer(CourseOfferResponseDTO courseOffer) {
        this.courseOffer = courseOffer;
    }

    public Set<CourseModuleResponseDTO> getCourseModules() {
        return courseModules;
    }

    public void setCourseModules(Set<CourseModuleResponseDTO> courseModules) {
        this.courseModules = courseModules;
    }

    public Set<StudentResponseDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentResponseDTO> students) {
        this.students = students;
    }
}
