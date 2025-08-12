package com.ayursetu.notification.controller;

import com.ayursetu.notification.dto.NotificationDto;
import com.ayursetu.notification.dto.EmailRequestDto;
import com.ayursetu.notification.dto.SmsRequestDto;
import com.ayursetu.notification.dto.PushNotificationDto;
import com.ayursetu.notification.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService notificationService;
    
    // Notification CRUD endpoints
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }
    
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        NotificationDto createdNotification = notificationService.createNotification(notificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable Long id, @Valid @RequestBody NotificationDto notificationDto) {
        NotificationDto updatedNotification = notificationService.updateNotification(id, notificationDto);
        return ResponseEntity.ok(updatedNotification);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDto> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByStatus(@PathVariable String status) {
        List<NotificationDto> notifications = notificationService.getNotificationsByStatus(status);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByType(@PathVariable String type) {
        List<NotificationDto> notifications = notificationService.getNotificationsByType(type);
        return ResponseEntity.ok(notifications);
    }
    
    @GetMapping("/channel/{channel}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByChannel(@PathVariable String channel) {
        List<NotificationDto> notifications = notificationService.getNotificationsByChannel(channel);
        return ResponseEntity.ok(notifications);
    }
    
    // Email notification endpoints
    @PostMapping("/email")
    public ResponseEntity<Void> sendEmail(@Valid @RequestBody EmailRequestDto emailRequest) {
        notificationService.sendEmail(emailRequest);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/email/bulk")
    public ResponseEntity<Void> sendBulkEmail(@Valid @RequestBody EmailRequestDto[] emailRequests) {
        notificationService.sendBulkEmail(emailRequests);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/email/template")
    public ResponseEntity<Void> sendTemplatedEmail(@Valid @RequestBody EmailRequestDto emailRequest) {
        notificationService.sendTemplatedEmail(emailRequest);
        return ResponseEntity.ok().build();
    }
    
    // SMS notification endpoints
    @PostMapping("/sms")
    public ResponseEntity<Void> sendSms(@Valid @RequestBody SmsRequestDto smsRequest) {
        notificationService.sendSms(smsRequest);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/sms/bulk")
    public ResponseEntity<Void> sendBulkSms(@Valid @RequestBody SmsRequestDto[] smsRequests) {
        notificationService.sendBulkSms(smsRequests);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/sms/template")
    public ResponseEntity<Void> sendTemplatedSms(@Valid @RequestBody SmsRequestDto smsRequest) {
        notificationService.sendTemplatedSms(smsRequest);
        return ResponseEntity.ok().build();
    }
    
    // Push notification endpoints
    @PostMapping("/push")
    public ResponseEntity<Void> sendPushNotification(@Valid @RequestBody PushNotificationDto pushNotification) {
        notificationService.sendPushNotification(pushNotification);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/push/bulk")
    public ResponseEntity<Void> sendBulkPushNotifications(@Valid @RequestBody PushNotificationDto[] pushNotifications) {
        notificationService.sendBulkPushNotifications(pushNotifications);
        return ResponseEntity.ok().build();
    }
    
    // Specific notification type endpoints
    @PostMapping("/welcome")
    public ResponseEntity<Void> sendWelcomeNotification(
            @RequestParam Long userId, 
            @RequestParam(required = false) String email, 
            @RequestParam(required = false) String phone) {
        notificationService.sendWelcomeNotification(userId, email, phone);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/appointment")
    public ResponseEntity<Void> sendAppointmentNotification(
            @RequestParam Long userId, @RequestParam String appointmentDetails) {
        notificationService.sendAppointmentNotification(userId, appointmentDetails);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/order")
    public ResponseEntity<Void> sendOrderNotification(
            @RequestParam Long userId, @RequestParam String orderDetails) {
        notificationService.sendOrderNotification(userId, orderDetails);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/payment")
    public ResponseEntity<Void> sendPaymentNotification(
            @RequestParam Long userId, @RequestParam String paymentDetails) {
        notificationService.sendPaymentNotification(userId, paymentDetails);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/emergency")
    public ResponseEntity<Void> sendEmergencyNotification(
            @RequestParam Long userId, @RequestParam String emergencyMessage) {
        notificationService.sendEmergencyNotification(userId, emergencyMessage);
        return ResponseEntity.ok().build();
    }
    
    // Notification status management endpoints
    @PutMapping("/{id}/status")
    public ResponseEntity<NotificationDto> updateNotificationStatus(
            @PathVariable Long id, @RequestParam String status) {
        NotificationDto updatedNotification = notificationService.updateNotificationStatus(id, status);
        return ResponseEntity.ok(updatedNotification);
    }
    
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markNotificationAsRead(id);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllNotificationsAsRead(@PathVariable Long userId) {
        notificationService.markAllNotificationsAsRead(userId);
        return ResponseEntity.ok().build();
    }
    
    // Analytics endpoints
    @GetMapping("/analytics/total")
    public ResponseEntity<Long> getTotalNotifications() {
        Long totalNotifications = notificationService.getTotalNotifications();
        return ResponseEntity.ok(totalNotifications);
    }
    
    @GetMapping("/analytics/by-status/{status}")
    public ResponseEntity<Long> getNotificationCountByStatus(@PathVariable String status) {
        Long count = notificationService.getNotificationCountByStatus(status);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/analytics/by-type/{type}")
    public ResponseEntity<Long> getNotificationCountByType(@PathVariable String type) {
        Long count = notificationService.getNotificationCountByType(type);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/analytics/by-channel/{channel}")
    public ResponseEntity<Long> getNotificationCountByChannel(@PathVariable String channel) {
        Long count = notificationService.getNotificationCountByChannel(channel);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/analytics/type-stats")
    public ResponseEntity<List<Object[]>> getNotificationTypeStats() {
        List<Object[]> stats = notificationService.getNotificationTypeStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/analytics/status-stats")
    public ResponseEntity<List<Object[]>> getNotificationStatusStats() {
        List<Object[]> stats = notificationService.getNotificationStatusStats();
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/analytics/channel-stats")
    public ResponseEntity<List<Object[]>> getNotificationChannelStats() {
        List<Object[]> stats = notificationService.getNotificationChannelStats();
        return ResponseEntity.ok(stats);
    }
}


