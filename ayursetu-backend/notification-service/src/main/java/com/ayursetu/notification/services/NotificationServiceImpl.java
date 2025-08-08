package com.ayursetu.notification.services;

import com.ayursetu.notification.dto.NotificationDto;
import com.ayursetu.notification.dto.EmailRequestDto;
import com.ayursetu.notification.dto.SmsRequestDto;
import com.ayursetu.notification.dto.PushNotificationDto;
import com.ayursetu.notification.entities.Notification;
import com.ayursetu.notification.exception.NotificationFailedException;
import com.ayursetu.notification.exception.ResourceNotFoundException;
import com.ayursetu.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final SmsService smsService;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    // Notification CRUD operations
    @Override
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        return modelMapper.map(notification, NotificationDto.class);
    }
    
    @Override
    @Transactional
    public NotificationDto createNotification(NotificationDto notificationDto) {
        // Manual mapping to handle enum conversion
        Notification notification = new Notification();
        notification.setUserId(notificationDto.getUserId());
        notification.setTitle(notificationDto.getTitle());
        notification.setMessage(notificationDto.getMessage());
        
        // Convert string to enum for type
        if (notificationDto.getType() != null) {
            notification.setType(Notification.NotificationType.valueOf(notificationDto.getType().toUpperCase()));
        }
        
        // Convert string to enum for status
        if (notificationDto.getStatus() != null) {
            notification.setStatus(Notification.NotificationStatus.valueOf(notificationDto.getStatus().toUpperCase()));
        }
        
        // Convert string to enum for channel
        if (notificationDto.getChannel() != null) {
            notification.setChannel(Notification.NotificationChannel.valueOf(notificationDto.getChannel().toUpperCase()));
        }
        
        // The @PrePersist will handle createdAt automatically
        
        Notification savedNotification = notificationRepository.save(notification);
        
        // Publish notification created event (with error handling)
        try {
            kafkaTemplate.send("notification-events", "notification-created", "Notification created: " + savedNotification.getId());
            log.info("Kafka notification created event sent for notification: {}", savedNotification.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification created event for notification: {}. Error: {}", savedNotification.getId(), e.getMessage());
        }
        
        return modelMapper.map(savedNotification, NotificationDto.class);
    }
    
    @Override
    @Transactional
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        
        // Manual mapping to handle enum conversion
        if (notificationDto.getUserId() != null) {
            existingNotification.setUserId(notificationDto.getUserId());
        }
        if (notificationDto.getTitle() != null) {
            existingNotification.setTitle(notificationDto.getTitle());
        }
        if (notificationDto.getMessage() != null) {
            existingNotification.setMessage(notificationDto.getMessage());
        }
        
        // Convert string to enum for type
        if (notificationDto.getType() != null) {
            existingNotification.setType(Notification.NotificationType.valueOf(notificationDto.getType().toUpperCase()));
        }
        
        // Convert string to enum for status
        if (notificationDto.getStatus() != null) {
            existingNotification.setStatus(Notification.NotificationStatus.valueOf(notificationDto.getStatus().toUpperCase()));
        }
        
        // Convert string to enum for channel
        if (notificationDto.getChannel() != null) {
            existingNotification.setChannel(Notification.NotificationChannel.valueOf(notificationDto.getChannel().toUpperCase()));
        }
        
        // No updatedAt field in current entity
        
        Notification updatedNotification = notificationRepository.save(existingNotification);
        
        // Publish notification updated event (with error handling)
        try {
            kafkaTemplate.send("notification-events", "notification-updated", "Notification updated: " + updatedNotification.getId());
            log.info("Kafka notification updated event sent for notification: {}", updatedNotification.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification updated event for notification: {}. Error: {}", updatedNotification.getId(), e.getMessage());
        }
        
        return modelMapper.map(updatedNotification, NotificationDto.class);
    }
    
    @Override
    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        
        notificationRepository.delete(notification);
        
        // Publish notification deleted event (with error handling)
        try {
            kafkaTemplate.send("notification-events", "notification-deleted", "Notification deleted: " + id);
            log.info("Kafka notification deleted event sent for notification: {}", id);
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification deleted event for notification: {}. Error: {}", id, e.getMessage());
        }
    }
    
    @Override
    public List<NotificationDto> getNotificationsByUserId(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<NotificationDto> getNotificationsByStatus(String status) {
        List<Notification> notifications = notificationRepository.findByStatus(Notification.NotificationStatus.valueOf(status.toUpperCase()));
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<NotificationDto> getNotificationsByType(String type) {
        List<Notification> notifications = notificationRepository.findByType(Notification.NotificationType.valueOf(type.toUpperCase()));
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<NotificationDto> getNotificationsByChannel(String channel) {
        List<Notification> notifications = notificationRepository.findByChannel(Notification.NotificationChannel.valueOf(channel.toUpperCase()));
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
    }
    
    // Email notifications
    @Override
    public void sendEmail(EmailRequestDto emailRequest) {
        try {
            emailService.sendEmail(emailRequest);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(emailRequest.getUserId());
            notificationDto.setTitle("Email Notification");
            notificationDto.setMessage(emailRequest.getSubject());
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("EMAIL");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Email sending failed", e);
            throw new NotificationFailedException("Email sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendBulkEmail(EmailRequestDto[] emailRequests) {
        emailService.sendBulkEmail(emailRequests);
    }
    
    @Override
    public void sendTemplatedEmail(EmailRequestDto emailRequest) {
        try {
            emailService.sendTemplatedEmail(emailRequest);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(emailRequest.getUserId());
            notificationDto.setTitle("Templated Email Notification");
            notificationDto.setMessage(emailRequest.getSubject());
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("EMAIL");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Templated email sending failed", e);
            throw new NotificationFailedException("Templated email sending failed: " + e.getMessage());
        }
    }
    
    // SMS notifications
    @Override
    public void sendSms(SmsRequestDto smsRequest) {
        try {
            smsService.sendSms(smsRequest);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(smsRequest.getUserId());
            notificationDto.setTitle("SMS Notification");
            notificationDto.setMessage(smsRequest.getMessage());
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("SMS");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("SMS sending failed", e);
            throw new NotificationFailedException("SMS sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendBulkSms(SmsRequestDto[] smsRequests) {
        smsService.sendBulkSms(smsRequests);
    }
    
    @Override
    public void sendTemplatedSms(SmsRequestDto smsRequest) {
        try {
            smsService.sendTemplatedSms(smsRequest);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(smsRequest.getUserId());
            notificationDto.setTitle("Templated SMS Notification");
            notificationDto.setMessage(smsRequest.getMessage());
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("SMS");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Templated SMS sending failed", e);
            throw new NotificationFailedException("Templated SMS sending failed: " + e.getMessage());
        }
    }
    
    // Push notifications
    @Override
    public void sendPushNotification(PushNotificationDto pushNotification) {
        try {
            // Implementation for push notification (Firebase, etc.)
            log.info("Sending push notification to user: {}", pushNotification.getUserId());
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(pushNotification.getUserId());
            notificationDto.setTitle(pushNotification.getTitle());
            notificationDto.setMessage(pushNotification.getBody());
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("PUSH");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Push notification sending failed", e);
            throw new NotificationFailedException("Push notification sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendBulkPushNotifications(PushNotificationDto[] pushNotifications) {
        for (PushNotificationDto pushNotification : pushNotifications) {
            try {
                sendPushNotification(pushNotification);
            } catch (Exception e) {
                log.error("Failed to send bulk push notification to user: {}", pushNotification.getUserId(), e);
            }
        }
    }
    
    // Specific notification types
    @Override
    public void sendWelcomeNotification(Long userId, String email, String phone) {
        try {
            // Send welcome email (with error handling)
            if (email != null && emailService.validateEmail(email)) {
                try {
                    emailService.sendWelcomeEmail(email, "User");
                    log.info("Welcome email sent successfully to: {}", email);
                } catch (Exception e) {
                    log.warn("Failed to send welcome email to: {}. Error: {}", email, e.getMessage());
                }
            }
            
            // Send welcome SMS (with error handling)
            if (phone != null && smsService.validatePhoneNumber(phone)) {
                try {
                    SmsRequestDto smsRequest = new SmsRequestDto();
                    smsRequest.setUserId(userId);
                    smsRequest.setPhoneNumber(phone);
                    smsRequest.setMessage("Welcome to AyurSetu! Your health partner. Download our app for better experience.");
                    sendSms(smsRequest);
                    log.info("Welcome SMS sent successfully to: {}", phone);
                } catch (Exception e) {
                    log.warn("Failed to send welcome SMS to: {}. Error: {}", phone, e.getMessage());
                }
            } else if (phone != null) {
                log.warn("Invalid phone number format: {}", phone);
            }
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(userId);
            notificationDto.setTitle("Welcome to AyurSetu");
            notificationDto.setMessage("Welcome to AyurSetu! Your health partner.");
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("IN_APP");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Welcome notification sending failed", e);
            // Don't throw exception, just log the error
        }
    }
    
    @Override
    public void sendAppointmentNotification(Long userId, String appointmentDetails) {
        try {
            // Send appointment confirmation email (with error handling)
            try {
                emailService.sendAppointmentConfirmationEmail("user@example.com", "User", appointmentDetails);
                log.info("Appointment confirmation email sent successfully");
            } catch (Exception e) {
                log.warn("Failed to send appointment confirmation email. Error: {}", e.getMessage());
            }
            
            // Send appointment reminder SMS (with error handling)
            try {
                String testPhoneNumber = "+919876543210";
                if (smsService.validatePhoneNumber(testPhoneNumber)) {
                    SmsRequestDto smsRequest = new SmsRequestDto();
                    smsRequest.setUserId(userId);
                    smsRequest.setPhoneNumber(testPhoneNumber);
                    smsService.sendAppointmentReminderSms(testPhoneNumber, appointmentDetails);
                    log.info("Appointment reminder SMS sent successfully");
                } else {
                    log.warn("Invalid test phone number format: {}", testPhoneNumber);
                }
            } catch (Exception e) {
                log.warn("Failed to send appointment reminder SMS. Error: {}", e.getMessage());
            }
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(userId);
            notificationDto.setTitle("Appointment Confirmation");
            notificationDto.setMessage("Your appointment has been confirmed: " + appointmentDetails);
            notificationDto.setType("CONSULTATION_BOOKED");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("IN_APP");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Appointment notification sending failed", e);
            // Don't throw exception, just log the error
        }
    }
    
    @Override
    public void sendOrderNotification(Long userId, String orderDetails) {
        try {
            // Send order confirmation email
            emailService.sendOrderConfirmationEmail("user@example.com", "User", orderDetails);
            
            // Send order status SMS
            SmsRequestDto smsRequest = new SmsRequestDto();
            smsRequest.setUserId(userId);
            smsRequest.setPhoneNumber("+919876543210");
            smsService.sendOrderStatusSms("+919876543210", orderDetails);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(userId);
            notificationDto.setTitle("Order Confirmation");
            notificationDto.setMessage("Your order has been confirmed: " + orderDetails);
            notificationDto.setType("ORDER_PLACED");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("IN_APP");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Order notification sending failed", e);
            throw new NotificationFailedException("Order notification sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendPaymentNotification(Long userId, String paymentDetails) {
        try {
            // Send payment confirmation email
            emailService.sendPaymentConfirmationEmail("user@example.com", "User", paymentDetails);
            
            // Send payment reminder SMS
            SmsRequestDto smsRequest = new SmsRequestDto();
            smsRequest.setUserId(userId);
            smsRequest.setPhoneNumber("+919876543210");
            smsService.sendPaymentReminderSms("+919876543210", paymentDetails);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(userId);
            notificationDto.setTitle("Payment Confirmation");
            notificationDto.setMessage("Your payment has been confirmed: " + paymentDetails);
            notificationDto.setType("PAYMENT_SUCCESS");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("IN_APP");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Payment notification sending failed", e);
            throw new NotificationFailedException("Payment notification sending failed: " + e.getMessage());
        }
    }
    
    @Override
    public void sendEmergencyNotification(Long userId, String emergencyMessage) {
        try {
            // Send emergency SMS
            SmsRequestDto smsRequest = new SmsRequestDto();
            smsRequest.setUserId(userId);
            smsRequest.setPhoneNumber("+919876543210");
            smsService.sendEmergencySms("+919876543210", emergencyMessage);
            
            // Create notification record
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setUserId(userId);
            notificationDto.setTitle("Emergency Alert");
            notificationDto.setMessage("EMERGENCY: " + emergencyMessage);
            notificationDto.setType("GENERAL");
            notificationDto.setStatus("SENT");
            notificationDto.setChannel("IN_APP");
            
            createNotification(notificationDto);
            
        } catch (Exception e) {
            log.error("Emergency notification sending failed", e);
            throw new NotificationFailedException("Emergency notification sending failed: " + e.getMessage());
        }
    }
    
    // Notification status management
    @Override
    @Transactional
    public NotificationDto updateNotificationStatus(Long id, String status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id));
        
        notification.setStatus(Notification.NotificationStatus.valueOf(status.toUpperCase()));
        // No updatedAt or sentAt fields in current entity
        
        Notification updatedNotification = notificationRepository.save(notification);
        
        // Publish notification status updated event (with error handling)
        try {
            kafkaTemplate.send("notification-events", "notification-status-updated", "Notification status updated: " + updatedNotification.getId());
            log.info("Kafka notification status updated event sent for notification: {}", updatedNotification.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification status updated event for notification: {}. Error: {}", updatedNotification.getId(), e.getMessage());
        }
        
        return modelMapper.map(updatedNotification, NotificationDto.class);
    }
    
    @Override
    @Transactional
    public void markNotificationAsRead(Long id) {
        updateNotificationStatus(id, "READ");
    }
    
    @Override
    @Transactional
    public void markAllNotificationsAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndStatus(userId, Notification.NotificationStatus.SENT);
        for (Notification notification : notifications) {
            notification.setStatus(Notification.NotificationStatus.READ);
            notificationRepository.save(notification);
        }
    }
    
    // Analytics
    @Override
    public Long getTotalNotifications() {
        return notificationRepository.count();
    }
    
    @Override
    public Long getNotificationCountByStatus(String status) {
        return notificationRepository.countByStatus(Notification.NotificationStatus.valueOf(status.toUpperCase()));
    }
    
    @Override
    public Long getNotificationCountByType(String type) {
        return notificationRepository.countByType(Notification.NotificationType.valueOf(type.toUpperCase()));
    }
    
    @Override
    public Long getNotificationCountByChannel(String channel) {
        return notificationRepository.countByChannel(Notification.NotificationChannel.valueOf(channel.toUpperCase()));
    }
    
    @Override
    public List<Object[]> getNotificationTypeStats() {
        return notificationRepository.getNotificationTypeStats();
    }
    
    @Override
    public List<Object[]> getNotificationStatusStats() {
        return notificationRepository.getNotificationStatusStats();
    }
    
    @Override
    public List<Object[]> getNotificationChannelStats() {
        return notificationRepository.getNotificationChannelStats();
    }
    
    // Kafka listeners for event-driven notifications
    @KafkaListener(topics = "user-events", groupId = "notification-service-group")
    public void handleUserEvents(String event) {
        log.info("Received user event: {}", event);
        // Process user events and send appropriate notifications
    }
    
    @KafkaListener(topics = "appointment-events", groupId = "notification-service-group")
    public void handleAppointmentEvents(String event) {
        log.info("Received appointment event: {}", event);
        // Process appointment events and send notifications
    }
    
    @KafkaListener(topics = "order-events", groupId = "notification-service-group")
    public void handleOrderEvents(String event) {
        log.info("Received order event: {}", event);
        // Process order events and send notifications
    }
    
    @KafkaListener(topics = "payment-events", groupId = "notification-service-group")
    public void handlePaymentEvents(String event) {
        log.info("Received payment event: {}", event);
        // Process payment events and send notifications
    }
}


