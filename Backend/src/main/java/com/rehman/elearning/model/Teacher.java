package com.rehman.elearning.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher extends CommonEntity{
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.setTeacher(this);
    }
    @Override
    public String toString() {
        return "Teacher{" +
                "userId=" + userId +
                ", user=" + user +
                '}';
    }
}