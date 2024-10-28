package com.sarfaraz.elearning.rest.dto.inbound;

import java.util.Set;

public class TeacherRequestDTO {
    private Long userId;
    private Set<Long> courseIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Long> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(Set<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
