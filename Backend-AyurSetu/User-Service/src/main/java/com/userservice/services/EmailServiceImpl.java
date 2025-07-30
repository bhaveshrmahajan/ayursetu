package com.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Hello,\n\n" +
                "You have requested to reset your password. Please use the following token to reset your password:\n\n" +
                "Reset Token: " + resetToken + "\n\n" +
                "If you did not request this password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "AyurSetu Team");
        
        try {
            mailSender.send(message);
            System.out.println("Password reset email sent to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send email to: " + to + " - " + e.getMessage());
        }
    }

    @Override
    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to AyurSetu");
        message.setText("Hello " + name + ",\n\n" +
                "Welcome to AyurSetu! Your account has been successfully created.\n\n" +
                "Thank you for joining us.\n\n" +
                "Best regards,\n" +
                "AyurSetu Team");
        
        try {
            mailSender.send(message);
            System.out.println("Welcome email sent to: " + to);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email to: " + to + " - " + e.getMessage());
        }
    }
} 