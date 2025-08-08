package com.ayursetu.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    
    private Long id;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must not exceed 200 characters")
    private String title;
    
    @NotBlank(message = "Message is required")
    @Size(max = 1000, message = "Message must not exceed 1000 characters")
    private String message;
    
    @NotBlank(message = "Type is required")
    @Pattern(regexp = "CONSULTATION_BOOKED|CONSULTATION_REMINDER|CONSULTATION_COMPLETED|ORDER_PLACED|ORDER_CONFIRMED|ORDER_SHIPPED|ORDER_DELIVERED|PAYMENT_SUCCESS|PAYMENT_FAILED|PRESCRIPTION_UPLOADED|MEDICINE_AVAILABLE|APPOINTMENT_CANCELLED|GENERAL", 
             message = "Type must be one of the valid notification types")
    private String type;
    
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "PENDING|SENT|DELIVERED|READ|FAILED", 
             message = "Status must be PENDING, SENT, DELIVERED, READ, or FAILED")
    private String status;
    
    @NotBlank(message = "Channel is required")
    @Pattern(regexp = "EMAIL|SMS|PUSH|IN_APP", 
             message = "Channel must be EMAIL, SMS, PUSH, or IN_APP")
    private String channel;
    
    private Map<String, Object> metadata;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private LocalDateTime sentAt;
    
    private String failureReason;
}


