package com.sarfaraz.elearning.rest.dto.outbound;

import java.util.Set;

public class TeacherResponseDTO {
    private Long userId;
    private UserResponseDTO user;
    private Set<CourseResponseDTO> courses;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public Set<CourseResponseDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseResponseDTO> courses) {
        this.courses = courses;
    }
}
