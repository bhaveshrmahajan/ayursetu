package com.ayursetu.doctor.services;

import com.ayursetu.doctor.dto.DoctorDto;
import com.ayursetu.doctor.entities.Doctor;

import java.util.List;

public interface DoctorService {
    
    DoctorDto createDoctor(DoctorDto doctorDto);
    
    DoctorDto getDoctorById(Long id);
    
    DoctorDto getDoctorByEmail(String email);
    
    List<DoctorDto> getAllDoctors();
    
    List<DoctorDto> getDoctorsBySpecialization(String specialization);
    
    List<DoctorDto> getDoctorsByCity(String city);
    
    List<DoctorDto> getAvailableDoctors();
    
    List<DoctorDto> getVerifiedDoctors();
    
    List<DoctorDto> getDoctorsBySpecializationAndCity(String specialization, String city);
    
    List<DoctorDto> getDoctorsByFeeRange(Double minFee, Double maxFee);
    
    List<String> getAllSpecializations();
    
    List<String> getAllCities();
    
    DoctorDto updateDoctor(Long id, DoctorDto doctorDto);
    
    void deleteDoctor(Long id);
    
    void updateAvailability(Long id, Boolean isAvailable);
    
    void updateVerificationStatus(Long id, Boolean isVerified);
}
