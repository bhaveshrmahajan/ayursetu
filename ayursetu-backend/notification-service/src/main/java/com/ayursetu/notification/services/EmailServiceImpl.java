package com.ayursetu.notification.services;

import com.ayursetu.notification.dto.EmailRequestDto;
import com.ayursetu.notification.exception.NotificationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;
    
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTls;
    
    @Override
    public void sendEmail(EmailRequestDto emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            
            helper.setFrom(emailRequest.getFrom() != null ? emailRequest.getFrom() : fromEmail);
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            
            if (emailRequest.isHtmlContent()) {
                helper.setText(emailRequest.getContent(), true);
            } else {
                helper.setText(emailRequest.getContent(), false);
            }
            
            if (emailRequest.getCc() != null) {
                helper.setCc(emailRequest.getCc());
            }
            
            if (emailRequest.getBcc() != null) {
                helper.setBcc(emailRequest.getBcc());
            }
            
            if (emailRequest.getReplyTo() != null) {
                helper.setReplyTo(emailRequest.getReplyTo());
            }
            
            mailSender.send(message);
            log.info("Email sent successfully to: {}", emailRequest.getTo());
            
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", emailRequest.getTo(), e);
            throw new NotificationFailedException("Email sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendTemplatedEmail(EmailRequestDto emailRequest) {
        try {
            if (emailRequest.getTemplateName() == null) {
                throw new NotificationFailedException("Template name is required for templated emails");
            }
            
            // Create context for template
            Context context = new Context();
            if (emailRequest.getTemplateData() != null) {
                for (Map.Entry<String, Object> entry : emailRequest.getTemplateData().entrySet()) {
                    context.setVariable(entry.getKey(), entry.getValue());
                }
            }
            
            // Process template
            String htmlContent = templateEngine.process(emailRequest.getTemplateName(), context);
            
            // Create email with HTML content
            EmailRequestDto htmlEmailRequest = new EmailRequestDto();
            htmlEmailRequest.setUserId(emailRequest.getUserId());
            htmlEmailRequest.setTo(emailRequest.getTo());
            htmlEmailRequest.setSubject(emailRequest.getSubject());
            htmlEmailRequest.setContent(htmlContent);
            htmlEmailRequest.setFrom(emailRequest.getFrom());
            htmlEmailRequest.setReplyTo(emailRequest.getReplyTo());
            htmlEmailRequest.setCc(emailRequest.getCc());
            htmlEmailRequest.setBcc(emailRequest.getBcc());
            htmlEmailRequest.setHtmlContent(true);
            
            sendEmail(htmlEmailRequest);
            
        } catch (Exception e) {
            log.error("Failed to send templated email to: {}", emailRequest.getTo(), e);
            throw new NotificationFailedException("Templated email sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendBulkEmail(EmailRequestDto[] emailRequests) {
        for (EmailRequestDto emailRequest : emailRequests) {
            try {
                sendEmail(emailRequest);
            } catch (Exception e) {
                log.error("Failed to send bulk email to: {}", emailRequest.getTo(), e);
                // Continue with other emails even if one fails
            }
        }
    }
    
    @Override
    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Basic email validation regex
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    @Override
    public void sendWelcomeEmail(String to, String userName) {
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(to);
        emailRequest.setSubject("Welcome to AyurSetu - Your Health Partner");
        emailRequest.setTemplateName("welcome-email");
        emailRequest.setTemplateData(Map.of(
            "userName", userName,
            "welcomeMessage", "Welcome to AyurSetu! We're excited to have you on board."
        ));
        emailRequest.setHtmlContent(true);
        
        sendTemplatedEmail(emailRequest);
    }
    
    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(to);
        emailRequest.setSubject("Password Reset Request - AyurSetu");
        emailRequest.setTemplateName("password-reset");
        emailRequest.setTemplateData(Map.of(
            "resetToken", resetToken,
            "resetLink", "https://ayursetu.com/reset-password?token=" + resetToken
        ));
        emailRequest.setHtmlContent(true);
        
        sendTemplatedEmail(emailRequest);
    }
    
    @Override
    public void sendAppointmentConfirmationEmail(String to, String userName, String appointmentDetails) {
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(to);
        emailRequest.setSubject("Appointment Confirmation - AyurSetu");
        emailRequest.setTemplateName("appointment-confirmation");
        emailRequest.setTemplateData(Map.of(
            "userName", userName,
            "appointmentDetails", appointmentDetails
        ));
        emailRequest.setHtmlContent(true);
        
        sendTemplatedEmail(emailRequest);
    }
    
    @Override
    public void sendOrderConfirmationEmail(String to, String userName, String orderDetails) {
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(to);
        emailRequest.setSubject("Order Confirmation - AyurSetu Pharmacy");
        emailRequest.setTemplateName("order-confirmation");
        emailRequest.setTemplateData(Map.of(
            "userName", userName,
            "orderDetails", orderDetails
        ));
        emailRequest.setHtmlContent(true);
        
        sendTemplatedEmail(emailRequest);
    }
    
    @Override
    public void sendPaymentConfirmationEmail(String to, String userName, String paymentDetails) {
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(to);
        emailRequest.setSubject("Payment Confirmation - AyurSetu");
        emailRequest.setTemplateName("payment-confirmation");
        emailRequest.setTemplateData(Map.of(
            "userName", userName,
            "paymentDetails", paymentDetails
        ));
        emailRequest.setHtmlContent(true);
        
        sendTemplatedEmail(emailRequest);
    }
}


