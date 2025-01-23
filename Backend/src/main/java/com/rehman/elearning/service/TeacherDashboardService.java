package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.TeacherDashboardDTO;

public interface TeacherDashboardService {
    public TeacherDashboardDTO getTeacherDashboard(Long teacherId);

}
