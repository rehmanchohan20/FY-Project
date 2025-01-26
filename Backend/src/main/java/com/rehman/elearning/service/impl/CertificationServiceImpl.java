package com.rehman.elearning.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.rehman.elearning.model.Certification;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.CourseProgress;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.CertificationRepository;
import com.rehman.elearning.repository.CourseProgressRepository;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.CertificationDTO;
import com.rehman.elearning.service.CertificationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CertificationServiceImpl implements CertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public CertificationDTO issueCertification(Long studentId, Long courseId) {
        // Fetch the student and course
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Fetch the most recent progress for the student in the course
        List<CourseProgress> progressList = courseProgressRepository.findLatestProgressByStudent_UserIdAndCourseModuleLesson_CourseModule_Course_Id(studentId, courseId);

        if (progressList.isEmpty()) {
            throw new IllegalArgumentException("Course progress not found");
        }

        // Get the latest progress (first element in the list)
        CourseProgress progress = progressList.get(0);

        // Check if the student has completed the course (progress should be 100%)
        if (progress.getProgressPercentage() < 100) {
            throw new IllegalStateException("Course not yet completed");
        }

        // Issue the certification
        Certification certification = new Certification();
        certification.setStudent(student);
        certification.setCourse(course);
        certification.setIssuedAt(Timestamp.valueOf(LocalDateTime.now()));
        certification.setCertificateUrl("http://example.com/certificates/" + studentId + "/" + courseId);

        // Save the certification
        certification = certificationRepository.save(certification);

        // Prepare the DTO to return
        CertificationDTO certificationDTO = new CertificationDTO();
        certificationDTO.setId(certification.getId());
        certificationDTO.setStudentId(studentId);
        certificationDTO.setCourseId(courseId);
        certificationDTO.setIssuedAt(certification.getIssuedAt());
        certificationDTO.setCertificateUrl(certification.getCertificateUrl());

        // Generate the PDF for the certificate
        ByteArrayOutputStream pdfStream = generateCertificatePdfUsingTemplate(student, course);

        // Send the certification email with the PDF attached
        sendCertificationEmail(student, pdfStream);

        return certificationDTO;
    }

    // Generate the certificate PDF
    private ByteArrayOutputStream generateCertificatePdfUsingTemplate(Student student, Course course) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            // Load the pre-designed template
            PdfDocument pdfDoc = new PdfDocument(
                    new PdfReader("C:/Users/rehma/Downloads/Kindness Certificate Template 4_removed.pdf"),
                    new PdfWriter(outputStream)
            );

            // Get the first page of the PDF
            PdfPage page = pdfDoc.getFirstPage();
            PdfCanvas pdfCanvas = new PdfCanvas(page);
            Rectangle pageSize = page.getPageSize();
            Canvas canvas = new Canvas(pdfCanvas, pageSize);

            // Add student name (Bold)
            canvas.showTextAligned(
                    new Paragraph(new Text(student.getUser().getName())
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                            .setFontSize(24)),
                    pageSize.getWidth() / 2, 400, TextAlignment.CENTER
            );

            // Add course title
            canvas.showTextAligned(
                    new Paragraph(new Text(course.getTitle())
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                            .setFontSize(18)),
                    pageSize.getWidth() / 2, 350, TextAlignment.CENTER
            );

            // Add issue date
            canvas.showTextAligned(
                    new Paragraph(new Text("Issued on: " + Timestamp.valueOf(LocalDateTime.now()))
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                            .setFontSize(12)),
                    pageSize.getWidth() / 2, 300, TextAlignment.CENTER
            );

            // Close the canvas and PDF document
            canvas.close();
            pdfDoc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    // Send the certification email with the PDF attached
    private void sendCertificationEmail(Student student, ByteArrayOutputStream pdfStream) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(student.getUser().getEmail());
            helper.setSubject("Your Certification for Course Completion");
            helper.setText("Congratulations! You have completed the course. Please find your certificate attached.");

            // Attach the PDF
            helper.addAttachment("Certificate.pdf", new ByteArrayDataSource(pdfStream.toByteArray(), "application/pdf"));

            mailSender.send(message);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
    }
}
