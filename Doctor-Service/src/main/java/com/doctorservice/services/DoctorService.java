package com.doctorservice.services;

import com.doctorservice.dto.*;
import java.util.List;

public interface DoctorService {
    DoctorDto registerDoctor(DoctorRegistrationDto registrationDto);
    
    DoctorDto getDoctorById(Long id);
    
    DoctorDto getDoctorByEmail(String email);
    
    List<DoctorDto> getAllDoctors();
    
    List<DoctorDto> getDoctorsBySpecialization(Long specializationId);
    
    List<DoctorDto> searchDoctors(String searchTerm);
    
    DoctorDto updateDoctorStatus(Long id, String status);
    
    List<SpecializationDto> getAllSpecializations();
    
    List<SpecializationDto> getActiveSpecializations();
    
    SpecializationDto createSpecialization(SpecializationDto specializationDto);
    
    DoctorReviewDto addReview(DoctorReviewDto reviewDto);
    
    List<DoctorReviewDto> getDoctorReviews(Long doctorId);
    
    Double getDoctorRating(Long doctorId);
    
    Integer getDoctorTotalReviews(Long doctorId);
    
    List<AvailabilityDto> getDoctorAvailability(Long doctorId);
    
    AvailabilityDto addAvailability(AvailabilityDto availabilityDto);
    
    void deleteAvailability(Long availabilityId);
} 