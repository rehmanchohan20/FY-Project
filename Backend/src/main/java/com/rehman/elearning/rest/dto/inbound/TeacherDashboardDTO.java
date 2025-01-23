package com.rehman.elearning.rest.dto.inbound;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TeacherDashboardDTO {
    // Course Statistics Fields
    private Long totalCourses;
    private Long totalStudentsEnrolled;
    private Long dailyEnrollments;
    private Double totalRevenue;

    // Enrollment Data Fields
    private List<EnrollmentData> enrollments;

    // Payment Data Fields
    private List<PaymentData> payments;

    // Recent Activities Fields
    private List<ActivityData> activities;

    // Inner classes for structured data

    public static class EnrollmentData {
        private String courseName;
        private Long courseEnrollments;
        private Timestamp createdAt;  // Use LocalDateTime instead of Date

        public EnrollmentData() {
        }

        public EnrollmentData(String courseName, Long courseEnrollments, Timestamp createdAt) {
            this.courseName = courseName;
            this.courseEnrollments = courseEnrollments;
            this.createdAt = createdAt;
        }

        // Getters and setters
        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public Long getCourseEnrollments() {
            return courseEnrollments;
        }

        public void setCourseEnrollments(Long courseEnrollments) {
            this.courseEnrollments = courseEnrollments;
        }

        public Timestamp getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
        }
    }


    public static class PaymentData {
        private Timestamp  revenueDate;
        private Double revenueAmount;

        // Getters and setters
        public Timestamp  getRevenueDate() {
            return revenueDate;
        }

        public void setRevenueDate(Timestamp  revenueDate) {
            this.revenueDate = revenueDate;
        }

        public Double getRevenueAmount() {
            return revenueAmount;
        }

        public void setRevenueAmount(Double revenueAmount) {
            this.revenueAmount = revenueAmount;
        }
    }

    public static class ActivityData {
        private String activity;
        private Timestamp activityDate;

        // Getters and setters
        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public Timestamp  getActivityDate() {
            return activityDate;
        }

        public void setActivityDate(Timestamp  activityDate) {
            this.activityDate = activityDate;
        }
    }

    // Getters and setters for main fields
    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Long getTotalStudentsEnrolled() {
        return totalStudentsEnrolled;
    }

    public void setTotalStudentsEnrolled(Long totalStudentsEnrolled) {
        this.totalStudentsEnrolled = totalStudentsEnrolled;
    }

    public Long getDailyEnrollments() {
        return dailyEnrollments;
    }

    public void setDailyEnrollments(Long dailyEnrollments) {
        this.dailyEnrollments = dailyEnrollments;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public List<EnrollmentData> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentData> enrollments) {
        this.enrollments = enrollments;
    }

    public List<PaymentData> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentData> payments) {
        this.payments = payments;
    }

    public List<ActivityData> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityData> activities) {
        this.activities = activities;
    }
}
