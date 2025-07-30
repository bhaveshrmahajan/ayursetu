package com.doctorservice.controller;

import com.doctorservice.dto.*;
import com.doctorservice.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<DoctorDto>> registerDoctor(@Valid @RequestBody DoctorRegistrationDto registrationDto) {
        log.info("Received doctor registration request for email: {}", registrationDto.getEmail());
        DoctorDto doctor = doctorService.registerDoctor(registrationDto);
        return ResponseEntity.ok(ApiResponse.success("Doctor registered successfully", doctor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorDto>> getDoctorById(@PathVariable Long id) {
        log.info("Fetching doctor with ID: {}", id);
        DoctorDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(ApiResponse.success("Doctor retrieved successfully", doctor));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<DoctorDto>> getDoctorByEmail(@PathVariable String email) {
        log.info("Fetching doctor with email: {}", email);
        DoctorDto doctor = doctorService.getDoctorByEmail(email);
        return ResponseEntity.ok(ApiResponse.success("Doctor retrieved successfully", doctor));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorDto>>> getAllDoctors() {
        log.info("Fetching all doctors");
        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(ApiResponse.success("Doctors retrieved successfully", doctors));
    }

    @GetMapping("/specialization/{specializationId}")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> getDoctorsBySpecialization(@PathVariable Long specializationId) {
        log.info("Fetching doctors by specialization ID: {}", specializationId);
        List<DoctorDto> doctors = doctorService.getDoctorsBySpecialization(specializationId);
        return ResponseEntity.ok(ApiResponse.success("Doctors retrieved successfully", doctors));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DoctorDto>>> searchDoctors(@RequestParam String query) {
        log.info("Searching doctors with query: {}", query);
        List<DoctorDto> doctors = doctorService.searchDoctors(query);
        return ResponseEntity.ok(ApiResponse.success("Doctors retrieved successfully", doctors));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<DoctorDto>> updateDoctorStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("Updating doctor status for ID: {} to: {}", id, status);
        DoctorDto doctor = doctorService.updateDoctorStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Doctor status updated successfully", doctor));
    }

    @GetMapping("/specializations")
    public ResponseEntity<ApiResponse<List<SpecializationDto>>> getAllSpecializations() {
        log.info("Fetching all specializations");
        List<SpecializationDto> specializations = doctorService.getAllSpecializations();
        return ResponseEntity.ok(ApiResponse.success("Specializations retrieved successfully", specializations));
    }

    @GetMapping("/specializations/active")
    public ResponseEntity<ApiResponse<List<SpecializationDto>>> getActiveSpecializations() {
        log.info("Fetching active specializations");
        List<SpecializationDto> specializations = doctorService.getActiveSpecializations();
        return ResponseEntity.ok(ApiResponse.success("Active specializations retrieved successfully", specializations));
    }

    @PostMapping("/specializations")
    public ResponseEntity<ApiResponse<SpecializationDto>> createSpecialization(@Valid @RequestBody SpecializationDto specializationDto) {
        log.info("Creating new specialization: {}", specializationDto.getName());
        SpecializationDto specialization = doctorService.createSpecialization(specializationDto);
        return ResponseEntity.ok(ApiResponse.success("Specialization created successfully", specialization));
    }

    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<DoctorReviewDto>> addReview(@Valid @RequestBody DoctorReviewDto reviewDto) {
        log.info("Adding review for doctor ID: {}", reviewDto.getDoctorId());
        DoctorReviewDto review = doctorService.addReview(reviewDto);
        return ResponseEntity.ok(ApiResponse.success("Review added successfully", review));
    }

    @GetMapping("/{doctorId}/reviews")
    public ResponseEntity<ApiResponse<List<DoctorReviewDto>>> getDoctorReviews(@PathVariable Long doctorId) {
        log.info("Fetching reviews for doctor ID: {}", doctorId);
        List<DoctorReviewDto> reviews = doctorService.getDoctorReviews(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Reviews retrieved successfully", reviews));
    }

    @GetMapping("/{doctorId}/rating")
    public ResponseEntity<ApiResponse<Double>> getDoctorRating(@PathVariable Long doctorId) {
        log.info("Fetching rating for doctor ID: {}", doctorId);
        Double rating = doctorService.getDoctorRating(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Rating retrieved successfully", rating));
    }

    @GetMapping("/{doctorId}/total-reviews")
    public ResponseEntity<ApiResponse<Integer>> getDoctorTotalReviews(@PathVariable Long doctorId) {
        log.info("Fetching total reviews for doctor ID: {}", doctorId);
        Integer totalReviews = doctorService.getDoctorTotalReviews(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Total reviews retrieved successfully", totalReviews));
    }

    @GetMapping("/{doctorId}/availability")
    public ResponseEntity<ApiResponse<List<AvailabilityDto>>> getDoctorAvailability(@PathVariable Long doctorId) {
        log.info("Fetching availability for doctor ID: {}", doctorId);
        List<AvailabilityDto> availability = doctorService.getDoctorAvailability(doctorId);
        return ResponseEntity.ok(ApiResponse.success("Availability retrieved successfully", availability));
    }

    @PostMapping("/availability")
    public ResponseEntity<ApiResponse<AvailabilityDto>> addAvailability(@Valid @RequestBody AvailabilityDto availabilityDto) {
        log.info("Adding availability for doctor ID: {}", availabilityDto.getDoctorId());
        AvailabilityDto availability = doctorService.addAvailability(availabilityDto);
        return ResponseEntity.ok(ApiResponse.success("Availability added successfully", availability));
    }

    @DeleteMapping("/availability/{availabilityId}")
    public ResponseEntity<ApiResponse<String>> deleteAvailability(@PathVariable Long availabilityId) {
        log.info("Deleting availability with ID: {}", availabilityId);
        doctorService.deleteAvailability(availabilityId);
        return ResponseEntity.ok(ApiResponse.success("Availability deleted successfully"));
    }
} 