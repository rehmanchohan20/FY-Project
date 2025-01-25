package com.rehman.elearning.service.impl;

import com.rehman.elearning.model.Payment;
import com.rehman.elearning.repository.CourseEnrollmentDataRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.PaymentRepository;
import com.rehman.elearning.rest.dto.inbound.TeacherDashboardDTO;
import com.rehman.elearning.service.TeacherDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

        Long CountEnrollment = courseRepository.countStudentsEnrolledToday(teacherId);

        dashboardDTO.setTotalCourses(courseRepository.countByTeacherUserId(teacherId));
        dashboardDTO.setTotalStudentsEnrolled(courseEnrollmentDataRepository.countTotalStudentsEnrolledByTeacher(teacherId));
        dashboardDTO.setDailyEnrollments(CountEnrollment);
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

        // Payment Data (Aggregated by course name)
        List<TeacherDashboardDTO.PaymentData> paymentData = paymentRepository.findByCourse_Teacher_UserId(teacherId)
                .stream()
                .collect(Collectors.groupingBy(
                        payment -> payment.getCourse().getTitle(),
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    String courseName = entry.getKey();
                    List<Payment> payments = entry.getValue();

                    double totalRevenue = payments.stream()
                            .mapToDouble(Payment::getAmount)
                            .sum();

                    List<Timestamp> revenueDates = payments.stream()
                            .map(payment -> payment.getCreatedAt())
                            .collect(Collectors.toList());

                    TeacherDashboardDTO.PaymentData data = new TeacherDashboardDTO.PaymentData();
                    data.setCourseName(courseName);
                    data.setRevenueAmount(totalRevenue);
                    data.setRevenueDates(revenueDates);
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
