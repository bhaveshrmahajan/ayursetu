package com.doctorservice.dto;

import com.doctorservice.entities.DoctorStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DoctorDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String licenseNumber;
    private String qualification;
    private Integer experienceYears;
    private BigDecimal consultationFee;
    private String bio;
    private DoctorStatus status;
    private SpecializationDto specialization;
    private Double rating;
    private Integer totalReviews;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 