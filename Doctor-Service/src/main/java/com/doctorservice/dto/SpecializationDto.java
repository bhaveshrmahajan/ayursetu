package com.doctorservice.dto;

import lombok.Data;

@Data
public class SpecializationDto {
    private Long id;
    private String name;
    private String description;
    private boolean active;
} 