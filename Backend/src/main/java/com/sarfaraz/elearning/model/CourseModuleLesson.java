package com.sarfaraz.elearning.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "course_module_lesson")
public class CourseModuleLesson extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long moduleId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;
    
    @Column(name = "duration")
    private String duration;

    @Column(name = "featured_lesson")
    private String featuredLesson;

    @Column(name = "IS_CONTENT_LOCK")
    private Boolean isContentLock;

    @Column(name="status")
    private String status;

    @Column(name = "Priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "course_module")
    private CourseModule courseModule;

    @ManyToMany
    @JoinTable(name = "course_module_lesson_media",
            joinColumns = @JoinColumn(name = "module_lession_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id"))
    private Set<Media> medias;

    @OneToMany(mappedBy = "courseModuleLesson",fetch = FetchType.LAZY)
    private Set<CourseProgress> courseProgress;

    public Long getId() {
        return moduleId;
    }

    public void setId(Long id) {
        this.moduleId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public CourseModule getCourseModule() {
        return courseModule;
    }

    public void setCourseModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }

    public String getFeaturedLesson() {
        return featuredLesson;
    }

    public void setFeaturedLesson(String featuredLesson) {
        this.featuredLesson = featuredLesson;
    }

    public Boolean getContentLock() {
        return isContentLock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContentLock(Boolean contentLock) {
        isContentLock = contentLock;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public void setMedias(Set<Media> medias) {
        this.medias = medias;
    }

    public Set<CourseProgress> getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(Set<CourseProgress> courseProgress) {
        this.courseProgress = courseProgress;
    }
}
