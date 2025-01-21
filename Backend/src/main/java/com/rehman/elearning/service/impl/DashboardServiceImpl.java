package com.rehman.elearning.service.impl;


import com.rehman.elearning.model.*;
import com.rehman.elearning.repository.*;
import com.rehman.elearning.rest.dto.inbound.*;
import com.rehman.elearning.rest.dto.outbound.*;
import com.rehman.elearning.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Autowired
    private CustomerSatisfactionRepository customerSatisfactionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public SalesSummaryResponseDTO getSalesSummary(SalesSummaryRequestDTO requestDTO) {
        SalesSummaryResponseDTO responseDTO = new SalesSummaryResponseDTO();

        // Aggregating Sales Data
        Double totalSales = paymentRepository.calculateTotalSales();
        Long totalCourses = courseRepository.count();
        Long todaysJoining = teacherRepository.countByJoinDateToday();
        Long teachersOnboard = teacherRepository.count();

        responseDTO.setTotalSales(totalSales);
        responseDTO.setTotalCourses(totalCourses);
        responseDTO.setTodaysJoining(todaysJoining);
        responseDTO.setTeachersOnboard(teachersOnboard);

        return responseDTO;
    }

    @Override
    public RevenueResponseDTO getRevenue(RevenueRequestDTO requestDTO) {
        RevenueResponseDTO responseDTO = new RevenueResponseDTO();

        // Aggregating Revenue Data (for the week)
        LocalDate startDate = LocalDate.of(2025, 1, 1); // Set your start date
        LocalDate endDate = LocalDate.of(2025, 1, 25); // Set your end date
        Timestamp startDateTime = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endDateTime = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));

        Double weeklyRevenue = paymentRepository.calculateWeeklyRevenue(startDateTime, endDateTime);
        responseDTO.setRevenue(weeklyRevenue);
        responseDTO.setDate("This week");

        return responseDTO;
    }

    @Override
    public TopCoursesResponseDTO getTopCourses(TopCoursesRequestDTO requestDTO) {
        TopCoursesResponseDTO responseDTO = new TopCoursesResponseDTO();

        // Fetching top courses based on the number of enrollments (or other criteria)
        List<Course> topCourses = courseRepository.findTopCoursesByEnrollments(); // Query for top courses based on enrollments
        Long topCourseEnrollments = topCourses.stream()
                .mapToLong(course -> course.getStudents().size()) // Calculate the number of students enrolled in each course
                .sum();

        responseDTO.setTopCourses(topCourses);
        responseDTO.setTotalEnrollments(topCourseEnrollments);

        return responseDTO;
    }

    @Override
    public VisitorInsightsResponseDTO getVisitorInsights(VisitorInsightRequestDTO requestDTO) {
        VisitorInsightsResponseDTO responseDTO = new VisitorInsightsResponseDTO();

        // Fetching Monthly Visitor Data
        Long visitorsThisMonth = paymentRepository.countVisitorsThisMonth();
        responseDTO.setMonth("January");
        responseDTO.setVisitors(visitorsThisMonth);

        return responseDTO;
    }



    @Override
    public CustomerSatisfactionResponseDTO getCustomerSatisfaction(CustomerSatisfactionRequestDTO requestDTO) {
        CustomerSatisfactionResponseDTO responseDTO = new CustomerSatisfactionResponseDTO();

        // Fetching Satisfaction Scores for Last Month and This Month
        LocalDate previousMonth = LocalDate.of(2025, 1, 1); // Set your previous month
        Timestamp previousMonthStart = Timestamp.valueOf(previousMonth.atStartOfDay());

        Double scoreLastMonth = customerSatisfactionRepository.getLastMonthScore(previousMonthStart);
        Double scoreThisMonth = customerSatisfactionRepository.getThisMonthScore();

        responseDTO.setScoreLastMonth(scoreLastMonth);
        responseDTO.setScoreThisMonth(scoreThisMonth);

        return responseDTO;
    }
}
