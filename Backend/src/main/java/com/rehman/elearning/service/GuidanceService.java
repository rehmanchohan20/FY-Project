package com.rehman.elearning.service;

import com.rehman.elearning.model.Course;

import com.rehman.elearning.model.Student;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponseDTO;

import java.util.List;

public interface GuidanceService {
    GuidanceResponseDTO createGuidanceForStudent(Student student, String question, List<Course> courses);
}