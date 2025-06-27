package com.TwoFactorAuthentication.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String recipientEmail, String otpCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("no-reply@yourdomain.com");  // Use a test email
            helper.setTo(recipientEmail);
            helper.setSubject("Your OTP Code");
            helper.setText("Your OTP code is: " + otpCode);

            mailSender.send(message);
            System.out.println("OTP email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
        }
    }
}
