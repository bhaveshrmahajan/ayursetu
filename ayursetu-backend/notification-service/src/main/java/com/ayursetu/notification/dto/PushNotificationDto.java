package com.ayursetu.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationDto {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Body is required")
    private String body;
    
    private String imageUrl;
    
    private String clickAction;
    
    private Map<String, String> data;
    
    private String topic;
    
    private String[] tokens;
    
    private boolean silent = false;
    
    private int priority = 10;
    
    private int timeToLive = 2419200; // 28 days in seconds
}


