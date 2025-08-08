package com.ayursetu.consultation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfo {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String qualification;
    private String licenseNumber;
    private String experience;
    private String bio;
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private Double consultationFee;
    private Boolean isAvailable;
    private Boolean isVerified;
}




