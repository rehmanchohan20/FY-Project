package com.sarfaraz.elearning.service;

import com.sarfaraz.elearning.rest.dto.inbound.CourseRequestDTO;
import com.sarfaraz.elearning.rest.dto.outbound.CourseResponseDTO;

import java.util.List;

public interface CourseService {
	CourseResponseDTO createCourse(CourseRequestDTO courseRequestDto);
	CourseResponseDTO updateCourse(Long id, CourseRequestDTO courseRequestDto);
	CourseResponseDTO getCourseById(Long id);
	List<CourseResponseDTO> getAllCourses();
	void deleteCourse(Long id);
	List<CourseResponseDTO> searchCourseByName(String courseName);
//	public void saveVideoUrl(Long courseId, String videoUrl);
}
