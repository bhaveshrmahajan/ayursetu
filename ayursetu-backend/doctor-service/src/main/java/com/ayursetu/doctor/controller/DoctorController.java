package com.ayursetu.doctor.controller;

import com.ayursetu.doctor.dto.DoctorDto;
import com.ayursetu.doctor.services.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Slf4j
public class DoctorController {
    
    private final DoctorService doctorService;
    
    @PostMapping
    public ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        log.info("Creating new doctor");
        DoctorDto createdDoctor = doctorService.createDoctor(doctorDto);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long id) {
        log.info("Fetching doctor with ID: {}", id);
        DoctorDto doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<DoctorDto> getDoctorByEmail(@PathVariable String email) {
        log.info("Fetching doctor with email: {}", email);
        DoctorDto doctor = doctorService.getDoctorByEmail(email);
        return ResponseEntity.ok(doctor);
    }
    
    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        log.info("Fetching all doctors");
        List<DoctorDto> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorDto>> getDoctorsBySpecialization(@PathVariable String specialization) {
        log.info("Fetching doctors by specialization: {}", specialization);
        List<DoctorDto> doctors = doctorService.getDoctorsBySpecialization(specialization);
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/city/{city}")
    public ResponseEntity<List<DoctorDto>> getDoctorsByCity(@PathVariable String city) {
        log.info("Fetching doctors by city: {}", city);
        List<DoctorDto> doctors = doctorService.getDoctorsByCity(city);
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<DoctorDto>> getAvailableDoctors() {
        log.info("Fetching available doctors");
        List<DoctorDto> doctors = doctorService.getAvailableDoctors();
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/verified")
    public ResponseEntity<List<DoctorDto>> getVerifiedDoctors() {
        log.info("Fetching verified doctors");
        List<DoctorDto> doctors = doctorService.getVerifiedDoctors();
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<DoctorDto>> getDoctorsBySpecializationAndCity(
            @RequestParam String specialization,
            @RequestParam String city) {
        log.info("Searching doctors by specialization: {} and city: {}", specialization, city);
        List<DoctorDto> doctors = doctorService.getDoctorsBySpecializationAndCity(specialization, city);
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/fee-range")
    public ResponseEntity<List<DoctorDto>> getDoctorsByFeeRange(
            @RequestParam Double minFee,
            @RequestParam Double maxFee) {
        log.info("Fetching doctors by fee range: {} - {}", minFee, maxFee);
        List<DoctorDto> doctors = doctorService.getDoctorsByFeeRange(minFee, maxFee);
        return ResponseEntity.ok(doctors);
    }
    
    @GetMapping("/specializations")
    public ResponseEntity<List<String>> getAllSpecializations() {
        log.info("Fetching all specializations");
        List<String> specializations = doctorService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }
    
    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        log.info("Fetching all cities");
        List<String> cities = doctorService.getAllCities();
        return ResponseEntity.ok(cities);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDto doctorDto) {
        log.info("Updating doctor with ID: {}", id);
        DoctorDto updatedDoctor = doctorService.updateDoctor(id, doctorDto);
        return ResponseEntity.ok(updatedDoctor);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        log.info("Deleting doctor with ID: {}", id);
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Void> updateAvailability(@PathVariable Long id, @RequestParam Boolean isAvailable) {
        log.info("Updating availability for doctor with ID: {} to {}", id, isAvailable);
        doctorService.updateAvailability(id, isAvailable);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{id}/verification")
    public ResponseEntity<Void> updateVerificationStatus(@PathVariable Long id, @RequestParam Boolean isVerified) {
        log.info("Updating verification status for doctor with ID: {} to {}", id, isVerified);
        doctorService.updateVerificationStatus(id, isVerified);
        return ResponseEntity.ok().build();
    }
}
