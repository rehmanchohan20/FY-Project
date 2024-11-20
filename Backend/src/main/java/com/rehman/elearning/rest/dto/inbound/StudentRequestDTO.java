package com.rehman.elearning.rest.dto.inbound;

import java.util.Set;

public class StudentRequestDTO {
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
