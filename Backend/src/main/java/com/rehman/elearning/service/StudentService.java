package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.StudentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.StudentResponseDTO;


import java.util.List;

public interface StudentService {
    StudentResponseDTO createStudent(StudentRequestDTO studentRequestDto);
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDto);
    StudentResponseDTO getStudentById(Long id);
    List<StudentResponseDTO> getAllStudents();
    void deleteStudent(Long id);
}