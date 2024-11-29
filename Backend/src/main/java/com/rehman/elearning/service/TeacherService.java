package com.rehman.elearning.service;

import com.rehman.elearning.model.Teacher;
import com.rehman.elearning.rest.dto.inbound.TeacherRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import com.rehman.elearning.rest.dto.outbound.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    TeacherResponseDTO createTeacher(TeacherRequestDTO teacherRequestDto);
    TeacherResponseDTO updateTeacher(Long id, TeacherRequestDTO teacherRequestDto);
    TeacherResponseDTO getTeacherById(Long id);
    List<TeacherResponseDTO> getAllTeachers();
    void deleteTeacher(Long id);
    public Teacher findTeacherById(Long teacherId);
    public List<CourseResponseDTO> getCoursesByTeacher(Long teacherId);
}
