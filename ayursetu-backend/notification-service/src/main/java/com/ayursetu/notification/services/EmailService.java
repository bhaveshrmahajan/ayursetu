package com.ayursetu.notification.services;

import com.ayursetu.notification.dto.EmailRequestDto;

public interface EmailService {
    
    void sendEmail(EmailRequestDto emailRequest);
    
    void sendTemplatedEmail(EmailRequestDto emailRequest);
    
    void sendBulkEmail(EmailRequestDto[] emailRequests);
    
    boolean validateEmail(String email);
    
    void sendWelcomeEmail(String to, String userName);
    
    void sendPasswordResetEmail(String to, String resetToken);
    
    void sendAppointmentConfirmationEmail(String to, String userName, String appointmentDetails);
    
    void sendOrderConfirmationEmail(String to, String userName, String orderDetails);
    
    void sendPaymentConfirmationEmail(String to, String userName, String paymentDetails);
}






