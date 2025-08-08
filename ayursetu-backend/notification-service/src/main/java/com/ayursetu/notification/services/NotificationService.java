package com.ayursetu.notification.services;

import com.ayursetu.notification.dto.NotificationDto;
import com.ayursetu.notification.dto.EmailRequestDto;
import com.ayursetu.notification.dto.SmsRequestDto;
import com.ayursetu.notification.dto.PushNotificationDto;

import java.util.List;

public interface NotificationService {
    
    // Notification CRUD operations
    List<NotificationDto> getAllNotifications();
    NotificationDto getNotificationById(Long id);
    NotificationDto createNotification(NotificationDto notificationDto);
    NotificationDto updateNotification(Long id, NotificationDto notificationDto);
    void deleteNotification(Long id);
    List<NotificationDto> getNotificationsByUserId(Long userId);
    List<NotificationDto> getNotificationsByStatus(String status);
    List<NotificationDto> getNotificationsByType(String type);
    List<NotificationDto> getNotificationsByChannel(String channel);
    
    // Email notifications
    void sendEmail(EmailRequestDto emailRequest);
    void sendBulkEmail(EmailRequestDto[] emailRequests);
    void sendTemplatedEmail(EmailRequestDto emailRequest);
    
    // SMS notifications
    void sendSms(SmsRequestDto smsRequest);
    void sendBulkSms(SmsRequestDto[] smsRequests);
    void sendTemplatedSms(SmsRequestDto smsRequest);
    
    // Push notifications
    void sendPushNotification(PushNotificationDto pushNotification);
    void sendBulkPushNotifications(PushNotificationDto[] pushNotifications);
    
    // Specific notification types
    void sendWelcomeNotification(Long userId, String email, String phone);
    void sendAppointmentNotification(Long userId, String appointmentDetails);
    void sendOrderNotification(Long userId, String orderDetails);
    void sendPaymentNotification(Long userId, String paymentDetails);
    void sendEmergencyNotification(Long userId, String emergencyMessage);
    
    // Notification status management
    NotificationDto updateNotificationStatus(Long id, String status);
    void markNotificationAsRead(Long id);
    void markAllNotificationsAsRead(Long userId);
    
    // Analytics
    Long getTotalNotifications();
    Long getNotificationCountByStatus(String status);
    Long getNotificationCountByType(String type);
    Long getNotificationCountByChannel(String channel);
    List<Object[]> getNotificationTypeStats();
    List<Object[]> getNotificationStatusStats();
    List<Object[]> getNotificationChannelStats();
}


