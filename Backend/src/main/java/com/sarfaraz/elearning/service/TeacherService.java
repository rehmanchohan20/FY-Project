package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.model.Teacher;
import com.sarfaraz.elearning.rest.dto.inbound.TeacherRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDto);
    TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO teacherRequestDto);
    TeacherResponseDTO getTeacherById(Long id);
    List<TeacherResponseDTO> getAllTeachers();
    void deleteTeacher(Long id);
    public Teacher findTeacherById(Long teacherId);
}
