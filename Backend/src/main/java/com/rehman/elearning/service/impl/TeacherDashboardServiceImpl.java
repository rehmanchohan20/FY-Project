package com.rehman.elearning.service.impl;

import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CourseEnrollmentData;
import com.rehman.elearning.model.Payment;
import com.rehman.elearning.repository.CourseEnrollmentDataRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.PaymentRepository;
import com.rehman.elearning.rest.dto.inbound.TeacherDashboardDTO;
import com.rehman.elearning.service.TeacherDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherDashboardServiceImpl implements TeacherDashboardService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseEnrollmentDataRepository courseEnrollmentDataRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public TeacherDashboardDTO getTeacherDashboard(Long teacherId) {
        TeacherDashboardDTO dashboardDTO = new TeacherDashboardDTO();

        // Course Statistics
        dashboardDTO.setTotalCourses(courseRepository.countByTeacherUserId(teacherId));
        dashboardDTO.setTotalStudentsEnrolled(courseEnrollmentDataRepository.countTotalEnrollmentsByTeacherId(teacherId));
        dashboardDTO.setDailyEnrollments(courseEnrollmentDataRepository.countDailyEnrollmentsByTeacherId(teacherId, LocalDate.now()));
        dashboardDTO.setTotalRevenue(paymentRepository.calculateTotalRevenueByTeacherId(teacherId));

        // Enrollment Data
        List<TeacherDashboardDTO.EnrollmentData> enrollmentData = courseEnrollmentDataRepository
                .findByCourseTeacherUserId(teacherId)
                .stream()
                .map(enrollment -> {
                    TeacherDashboardDTO.EnrollmentData data = new TeacherDashboardDTO.EnrollmentData();
                    data.setCourseName(enrollment.getCourse().getTitle());
                    data.setCourseEnrollments(enrollment.getEnrollmentCount());
                    data.setCreatedAt(enrollment.getCourse().getCreatedAt());
                    return data;
                })
                .collect(Collectors.toList());
        dashboardDTO.setEnrollments(enrollmentData);

        // Payment Data
        List<TeacherDashboardDTO.PaymentData> paymentData = paymentRepository.findByCourse_Teacher_UserId(teacherId)
                .stream()
                .map(payment -> {
                    TeacherDashboardDTO.PaymentData data = new TeacherDashboardDTO.PaymentData();
                    data.setRevenueDate(payment.getCreatedAt());
                    data.setRevenueAmount(payment.getAmount());
                    return data;
                })
                .collect(Collectors.toList());
        dashboardDTO.setPayments(paymentData);

        // Recent Activities
        List<TeacherDashboardDTO.ActivityData> activityData = courseRepository.findByTeacherUserId(teacherId)
                .stream()
                .map(course -> {
                    TeacherDashboardDTO.ActivityData data = new TeacherDashboardDTO.ActivityData();
                    data.setActivity("Course Created: " + course.getTitle());
                    data.setActivityDate(course.getCreatedAt());
                    return data;
                })
                .collect(Collectors.toList());
        dashboardDTO.setActivities(activityData);

        return dashboardDTO;
    }

}
