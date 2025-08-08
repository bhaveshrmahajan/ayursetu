package com.ayursetu.consultation.controller;

import com.ayursetu.consultation.dto.ConsultationDto;
import com.ayursetu.consultation.entities.Consultation;
import com.ayursetu.consultation.services.ConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ConsultationController {
    
    private final ConsultationService consultationService;
    
    @PostMapping
    public ResponseEntity<ConsultationDto> createConsultation(@Valid @RequestBody ConsultationDto consultationDto) {
        log.info("Creating new consultation");
        ConsultationDto createdConsultation = consultationService.createConsultation(consultationDto);
        return new ResponseEntity<>(createdConsultation, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDto> getConsultationById(@PathVariable Long id) {
        log.info("Fetching consultation with ID: {}", id);
        ConsultationDto consultation = consultationService.getConsultationById(id);
        return ResponseEntity.ok(consultation);
    }
    
    @GetMapping
    public ResponseEntity<List<ConsultationDto>> getAllConsultations() {
        log.info("Fetching all consultations");
        List<ConsultationDto> consultations = consultationService.getAllConsultations();
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByUserId(@PathVariable Long userId) {
        log.info("Fetching consultations for user: {}", userId);
        List<ConsultationDto> consultations = consultationService.getConsultationsByUserId(userId);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByDoctorId(@PathVariable Long doctorId) {
        log.info("Fetching consultations for doctor: {}", doctorId);
        List<ConsultationDto> consultations = consultationService.getConsultationsByDoctorId(doctorId);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByStatus(@PathVariable Consultation.ConsultationStatus status) {
        log.info("Fetching consultations by status: {}", status);
        List<ConsultationDto> consultations = consultationService.getConsultationsByStatus(status);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByUserIdAndStatus(
            @PathVariable Long userId,
            @PathVariable Consultation.ConsultationStatus status) {
        log.info("Fetching consultations for user: {} with status: {}", userId, status);
        List<ConsultationDto> consultations = consultationService.getConsultationsByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/doctor/{doctorId}/status/{status}")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByDoctorIdAndStatus(
            @PathVariable Long doctorId,
            @PathVariable Consultation.ConsultationStatus status) {
        log.info("Fetching consultations for doctor: {} with status: {}", doctorId, status);
        List<ConsultationDto> consultations = consultationService.getConsultationsByDoctorIdAndStatus(doctorId, status);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Fetching consultations between {} and {}", startDate, endDate);
        List<ConsultationDto> consultations = consultationService.getConsultationsByDateRange(startDate, endDate);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/doctor/{doctorId}/date-range")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByDoctorIdAndDateRange(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Fetching consultations for doctor: {} between {} and {}", doctorId, startDate, endDate);
        List<ConsultationDto> consultations = consultationService.getConsultationsByDoctorIdAndDateRange(doctorId, startDate, endDate);
        return ResponseEntity.ok(consultations);
    }
    
    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<ConsultationDto>> getConsultationsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Fetching consultations for user: {} between {} and {}", userId, startDate, endDate);
        List<ConsultationDto> consultations = consultationService.getConsultationsByUserIdAndDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(consultations);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDto> updateConsultation(@PathVariable Long id, @Valid @RequestBody ConsultationDto consultationDto) {
        log.info("Updating consultation with ID: {}", id);
        ConsultationDto updatedConsultation = consultationService.updateConsultation(id, consultationDto);
        return ResponseEntity.ok(updatedConsultation);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        log.info("Deleting consultation with ID: {}", id);
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateConsultationStatus(
            @PathVariable Long id,
            @RequestParam Consultation.ConsultationStatus status) {
        log.info("Updating consultation status for ID: {} to {}", id, status);
        consultationService.updateConsultationStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{id}/diagnosis")
    public ResponseEntity<Void> updateConsultationDiagnosis(
            @PathVariable Long id,
            @RequestParam String diagnosis,
            @RequestParam String prescription,
            @RequestParam String notes) {
        log.info("Updating consultation diagnosis for ID: {}", id);
        consultationService.updateConsultationDiagnosis(id, diagnosis, prescription, notes);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<List<ConsultationDto>> getOverdueConsultations() {
        log.info("Fetching overdue consultations");
        List<ConsultationDto> consultations = consultationService.getOverdueConsultations();
        return ResponseEntity.ok(consultations);
    }
    
    @PostMapping("/{id}/meeting-link")
    public ResponseEntity<Void> generateMeetingLink(@PathVariable Long id) {
        log.info("Generating meeting link for consultation: {}", id);
        consultationService.generateMeetingLink(id);
        return ResponseEntity.ok().build();
    }
}
