package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.TeacherDashboardDTO;
import com.rehman.elearning.service.TeacherDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher-dashboard")
public class TeacherDashboardController {

    @Autowired
    private TeacherDashboardService teacherDashboardService;

    @GetMapping("/{teacherId}")
    public TeacherDashboardDTO getTeacherDashboard(@PathVariable Long teacherId) {
        return teacherDashboardService.getTeacherDashboard(teacherId);
    }
}

