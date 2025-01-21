package com.rehman.elearning.rest;

import com.rehman.elearning.rest.dto.inbound.*;
import com.rehman.elearning.rest.dto.outbound.*;
import com.rehman.elearning.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class AdminDashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/sales-summary")
    public SalesSummaryResponseDTO getSalesSummary(SalesSummaryRequestDTO requestDTO) {
        return dashboardService.getSalesSummary(requestDTO);
    }

    @GetMapping("/customer-satisfaction")
    public CustomerSatisfactionResponseDTO getCustomerSatisfaction(CustomerSatisfactionRequestDTO requestDTO) {
        return dashboardService.getCustomerSatisfaction(requestDTO);
    }

    @GetMapping("/revenue")
    public RevenueResponseDTO getRevenue(RevenueRequestDTO requestDTO) {
        return dashboardService.getRevenue(requestDTO);
    }

    @GetMapping("/top-courses")
    public ResponseEntity<TopCoursesResponseDTO> getTopCourses(TopCoursesRequestDTO requestDTO) {
        TopCoursesResponseDTO responseDTO = dashboardService.getTopCourses(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/visitor-insights")
    public VisitorInsightsResponseDTO getVisitorInsights(VisitorInsightRequestDTO requestDTO) {
        return dashboardService.getVisitorInsights(requestDTO);
    }
}
