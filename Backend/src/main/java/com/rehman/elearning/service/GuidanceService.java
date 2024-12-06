package com.rehman.elearning.service;

import com.rehman.elearning.model.Course;

import com.rehman.elearning.model.Student;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponse;

import java.util.List;

public interface GuidanceService {
    GuidanceResponse createGuidanceForStudent(Student student, String question, List<Course> courses);
}