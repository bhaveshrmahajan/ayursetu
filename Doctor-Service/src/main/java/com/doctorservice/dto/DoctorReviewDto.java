package com.doctorservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DoctorReviewDto {
    private Long id;
    private Long doctorId;
    private String patientEmail;
    private String patientName;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;
    
    private String comment;
    private LocalDateTime createdAt;
} 