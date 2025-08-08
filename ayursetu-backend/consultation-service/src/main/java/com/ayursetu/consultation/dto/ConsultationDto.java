package com.ayursetu.consultation.dto;

import com.ayursetu.consultation.entities.Consultation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDto {
    
    private Long id;
    
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    
    @NotNull(message = "Appointment date time is required")
    @Future(message = "Appointment date must be in the future")
    private LocalDateTime appointmentDateTime;
    
    private Consultation.ConsultationStatus status = Consultation.ConsultationStatus.SCHEDULED;
    
    @NotNull(message = "Consultation type is required")
    private Consultation.ConsultationType type;
    
    private String symptoms;
    
    private String diagnosis;
    
    private String prescription;
    
    private String notes;
    
    @NotNull(message = "Consultation fee is required")
    @DecimalMin(value = "0.0", message = "Consultation fee must be positive")
    // Consultation fee will be fetched from doctor service
    // This field is for display purposes only
    private Double consultationFee;
    
    private String meetingLink;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
