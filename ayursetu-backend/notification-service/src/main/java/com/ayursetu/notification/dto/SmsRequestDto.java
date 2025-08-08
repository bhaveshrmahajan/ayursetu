package com.ayursetu.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequestDto {
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phoneNumber;
    
    @NotBlank(message = "Message is required")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\.,!?@#$%&*()+=:;\"'<>\\[\\]{}|\\\\/~`]+$", 
             message = "Message contains invalid characters")
    private String message;
    
    private String templateId;
    
    private Map<String, Object> templateData;
    
    private String senderId;
    
    private String countryCode = "+91";
    
    private boolean priority = false;
}


