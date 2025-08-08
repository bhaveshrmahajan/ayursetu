package com.ayursetu.notification.services;

import com.ayursetu.notification.dto.SmsRequestDto;

public interface SmsService {
    
    void sendSms(SmsRequestDto smsRequest);
    
    void sendBulkSms(SmsRequestDto[] smsRequests);
    
    void sendTemplatedSms(SmsRequestDto smsRequest);
    
    boolean validatePhoneNumber(String phoneNumber);
    
    void sendOtpSms(String phoneNumber, String otp);
    
    void sendAppointmentReminderSms(String phoneNumber, String appointmentDetails);
    
    void sendOrderStatusSms(String phoneNumber, String orderStatus);
    
    void sendPaymentReminderSms(String phoneNumber, String paymentDetails);
    
    void sendEmergencySms(String phoneNumber, String emergencyMessage);
}






