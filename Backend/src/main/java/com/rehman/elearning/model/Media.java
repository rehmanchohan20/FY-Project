package com.rehman.elearning.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "media")
public class Media extends  CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "url")
    private String url;
    @Column(name = "type")
    private String type;
    @Column(name = "duration")
    private String duration;

    @ManyToMany(mappedBy = "medias", cascade = CascadeType.ALL)
    private Set<CourseModuleLesson> courseModuleLessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Set<CourseModuleLesson> getCourseModuleLessons() {
        return courseModuleLessons;
    }

    public void setCourseModuleLessons(Set<CourseModuleLesson> courseModuleLessons) {
        this.courseModuleLessons = courseModuleLessons;
    }

}
