package com.sarfaraz.elearning.service.impl;

import com.sarfaraz.elearning.model.Student;
import com.sarfaraz.elearning.repository.StudentRepository;
import com.sarfaraz.elearning.rest.dto.inbound.StudentRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.StudentResponseDTO;
import com.sarfaraz.elearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDto) {
        Student student = new Student();
        // Map fields from studentRequestDto to student entity
        Student savedStudent = studentRepository.save(student);
        return mapToResponse(savedStudent);
    }

    @Override
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        // Update fields
        Student updatedStudent = studentRepository.save(student);
        return mapToResponse(updatedStudent);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToResponse(student);
    }

    @Override
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentResponseDTO mapToResponse(Student student) {
        StudentResponseDTO responseDto = new StudentResponseDTO();
        responseDto.setUserId(student.getUserId());
        // Set other fields
        return responseDto;
    }
}
