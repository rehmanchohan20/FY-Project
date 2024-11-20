package com.rehman.elearning.service;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;

import java.util.List;

public interface CourseService {
	CourseResponseDTO createCourse(CourseRequestDTO courseRequestDTO);
	CourseResponseDTO getCourseById(Long courseId);
	List<CourseResponseDTO> getAllCourses();
	CourseResponseDTO updateCourse(Long courseId, CourseRequestDTO request);
	void deleteCourse(Long courseId);
	List<CourseResponseDTO> searchCourseByName(String courseName);
	List<Course> getCoursesByKeyword(String keyword);
}
