package com.rehman.elearning.service;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.rest.dto.inbound.CourseRequestDTO;
import com.rehman.elearning.rest.dto.outbound.CourseResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

	/**
	 * Creates a new course without thumbnail.
	 *
	 * @param request the course request data
	 * @return the created course response DTO
	 */
	CourseResponseDTO createCourse(CourseRequestDTO request);

	/**
	 * Uploads the thumbnail for the given course.
	 *
	 * @param courseId the ID of the course
	 * @param thumbnail the thumbnail file to upload
	 * @return the URL of the uploaded thumbnail
	 */
	String uploadThumbnail(Long courseId, MultipartFile thumbnail);

	/**
	 * Retrieves a course by its ID.
	 *
	 * @param courseId the ID of the course
	 * @return the course response DTO
	 */
	CourseResponseDTO getCourseById(Long courseId);

	/**
	 * Retrieves all courses.
	 *
	 * @return a list of course response DTOs
	 */
	List<CourseResponseDTO> getAllCourses();

	/**
	 * Updates an existing course without changing the thumbnail.
	 *
	 * @param courseId the ID of the course to update
	 * @param request the updated course data
	 * @return the updated course response DTO
	 */
	CourseResponseDTO updateCourse(Long courseId, CourseRequestDTO request);

	/**
	 * Deletes a course by its ID.
	 *
	 * @param courseId the ID of the course to delete
	 */
	void deleteCourse(Long courseId);

	/**
	 * Searches for courses by their title.
	 *
	 * @param courseName the course name or part of it
	 * @return a list of course response DTOs
	 */
	List<CourseResponseDTO> searchCourseByName(String courseName);

	/**
	 * Searches for courses by a keyword (title or description).
	 *
	 * @param keyword the search keyword
	 * @return a list of courses matching the keyword
	 */
	List<Course> getCoursesByKeyword(String keyword);


	public List<CourseRequestDTO> getAvailableCoursesForStudent(Long studentId);



	public List<CourseResponseDTO> getCoursesByCategory(CategoryEnum category);
}
