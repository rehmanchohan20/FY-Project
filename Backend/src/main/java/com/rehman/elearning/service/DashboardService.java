package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.*;
import com.rehman.elearning.rest.dto.outbound.*;

public interface DashboardService {

    SalesSummaryResponseDTO getSalesSummary(SalesSummaryRequestDTO requestDTO);

    RevenueResponseDTO getRevenue(RevenueRequestDTO requestDTO);

    TopCoursesResponseDTO getTopCourses(TopCoursesRequestDTO requestDTO);

    VisitorInsightsResponseDTO getVisitorInsights(VisitorInsightRequestDTO requestDTO);

    CustomerSatisfactionResponseDTO getCustomerSatisfaction(CustomerSatisfactionRequestDTO requestDTO);
}
