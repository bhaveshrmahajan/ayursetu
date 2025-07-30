package com.userservice.services;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetToken);
    void sendWelcomeEmail(String to, String name);
} 