package com.bhagyashri.codebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Verify Your Email Address");
            message.setText("Thank you for registering! Please click the link below to verify your email address:\n\n" + 
                          verificationLink + "\n\n" +
                          "This link will expire in 24 hours.\n\n" +
                          "If you didn't create this account, please ignore this email.");
            
            mailSender.send(message);
            logger.info("Verification email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send verification email to: " + to, e);
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    public void sendResetLink(String to, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n\n" + resetLink + 
                          "\n\nThis link will expire in 15 minutes.\n\n" +
                          "If you didn't request this, please ignore this email.");
            
            mailSender.send(message);
            logger.info("Password reset email sent successfully to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send password reset email to: " + to, e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}
