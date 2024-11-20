package com.rehman.elearning.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class  EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("IntelliGuide E-learning <intelliguideelearning@gmail.com>");
        messageHelper.setTo(email) ;
        messageHelper.setSubject("Email verification code: " + otp);

        // Fixing the multiline string formatting
        String content = """
                <div>
                    <p>IntelliGuide E-learning Platform</p>
                    <h3>Verify your OTP</h3>
                    <p>IntelliGuide received a request to reset the password for the account associated with <strong>%s</strong>.</p>
                    <p>Use the code below to reset your password:</p>
                    <h2>%s</h2>
                    <p>This code will expire in 15 minute.</p>
                    <p>If you didn't request a password reset, you can safely ignore this email.</p>
                </div>
            """.formatted(email, otp);
        messageHelper.setText(content, true);  // Setting true for HTML content
        javaMailSender.send(mimeMessage); // Send the email after setting it up
    }

    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setFrom("IntelliGuide E-learning <intelliguideelearning@gmail.com>");
        messageHelper.setTo(email);
        messageHelper.setSubject("Set Password");

        String content = """
            <div><a href="http://localhost:8080/set-password?email=%s" target="_blank">Click To Set New Password</a></div>
            """.formatted(email);
        messageHelper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendTicketGeneratedEmail(String email, Long ticketId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("IntelliGuide E-learning <intelliguideelearning@gmail.com>");
        messageHelper.setTo(email);
        messageHelper.setSubject("Ticket Generated: #" + ticketId);

        String content = """
        <div>
            <p>Your ticket has been generated successfully.</p>
            <p>Ticket ID: #%s</p>
            <p>We will notify you once the issue is resolved.</p>
        </div>
    """.formatted(ticketId);
        messageHelper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }

    public void sendTicketResolvedEmail(String email, Long ticketId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("IntelliGuide E-learning <intelliguideelearning@gmail.com>");
        messageHelper.setTo(email);
        messageHelper.setSubject("Ticket Resolved: #" + ticketId);

        String content = """
        <div>
            <p>Your ticket has been resolved.</p>
            <p>Ticket ID: #%s</p>
            <p>Thank you for your patience.</p>
        </div>
    """.formatted(ticketId);
        messageHelper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }



}