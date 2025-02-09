package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.CategoryEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Guidance;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.GuidanceRepository;
import com.rehman.elearning.rest.dto.outbound.GuidanceResponseDTO;
import com.rehman.elearning.service.GuidanceService;
import com.rehman.elearning.util.KeywordUtil; // Importing the utility class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GuidanceServiceImpl implements GuidanceService {

    @Autowired
    private GuidanceRepository guidanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public GuidanceResponseDTO createGuidanceForStudent(Student student, String question, List<Course> courses) {
        String whatsappLink = null;

        // Check if question contains any of the response triggers (case-insensitive match)
        if (KeywordUtil.RESPONSE_TRIGGERS.stream()
                .anyMatch(trigger -> question.toLowerCase().contains(trigger.toLowerCase()))) {
            System.out.println("this hook hits");
            whatsappLink = "https://wa.me/03113865205?text=Hello, I need further guidance regarding my course!";
            System.out.println("WhatsApp Link: " + whatsappLink);
            return new GuidanceResponseDTO(whatsappLink); // Returning the WhatsApp link response
        }

        // Clean the question for better matching
        String cleanedQuestion = question.trim().replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();

        // Create a map of categories to their corresponding keyword sets
        Map<CategoryEnum, Set<String>> categoryKeywords = Map.of(
                CategoryEnum.Programming, KeywordUtil.PROGRAMMING_KEYWORDS,
                CategoryEnum.Design, KeywordUtil.DESIGN_KEYWORDS,
                CategoryEnum.DigitalMarketing, KeywordUtil.DIGITAL_MARKETING_KEYWORDS,
                CategoryEnum.Others, KeywordUtil.OTHERS_KEYWORDS
        );


        // Find relevant courses if not provided
        if (courses == null || courses.isEmpty()) {
            courses = findRelevantCourses(cleanedQuestion);
        }

        // Create and save the Guidance entity
        Guidance guidance = new Guidance();
        guidance.setStudent(student);
        guidance.setQuestion(question);
        guidance.setCourses(courses);
        guidance.setNextStepRecommendation("Based on your current progress, you should proceed with these steps.");
        guidance.setCreatedBy(UserCreatedBy.Self);
        guidance = guidanceRepository.save(guidance);

        // Map course details for response
        List<Map<String, Object>> courseDetails = courses.stream()
                .map(course -> {
                    Map<String, Object> courseMap = new HashMap<>();
                    courseMap.put("courseId", course.getId());
                    courseMap.put("courseTitle", course.getTitle());
                    return courseMap;
                })
                .collect(Collectors.toList());

        guidance.setAnswer("Personalized answer based on your query.");

        // Return full response
        return new GuidanceResponseDTO(
                guidance.getId(),  // Guidance ID
                guidance.getAnswer(),
                guidance.getNextStepRecommendation(),
                courseDetails,
                null  // No WhatsApp link if not triggered
        );
    }

    private List<Course> findRelevantCourses(String question) {
        String cleanedQuestion = question.trim().replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
        System.out.println("Cleaned question: " + cleanedQuestion);

        Map<CategoryEnum, Set<String>> categoryKeywords = Map.of(
                CategoryEnum.Programming, KeywordUtil.PROGRAMMING_KEYWORDS,
                CategoryEnum.Design, KeywordUtil.DESIGN_KEYWORDS,
                CategoryEnum.DigitalMarketing, KeywordUtil.DIGITAL_MARKETING_KEYWORDS,
                CategoryEnum.Others, KeywordUtil.OTHERS_KEYWORDS
        );

        CategoryEnum matchedCategory = categoryKeywords.entrySet().stream()
                .filter(entry -> entry.getValue().stream()
                        .anyMatch(keyword -> Arrays.asList(cleanedQuestion.split(" ")).contains(keyword.toLowerCase()))) // Match whole words
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        System.out.println("Matched Category: " + matchedCategory);
        if (matchedCategory != null) {
            return courseRepository.findByCategory(matchedCategory);
        }

        return courseRepository.findAll(); // Fallback case
    }



}
