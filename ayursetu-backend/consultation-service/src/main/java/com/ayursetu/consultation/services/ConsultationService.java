package com.ayursetu.consultation.services;

import com.ayursetu.consultation.dto.ConsultationDto;
import com.ayursetu.consultation.entities.Consultation;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationService {
    
    ConsultationDto createConsultation(ConsultationDto consultationDto);
    
    ConsultationDto getConsultationById(Long id);
    
    List<ConsultationDto> getAllConsultations();
    
    List<ConsultationDto> getConsultationsByUserId(Long userId);
    
    List<ConsultationDto> getConsultationsByDoctorId(Long doctorId);
    
    List<ConsultationDto> getConsultationsByStatus(Consultation.ConsultationStatus status);
    
    List<ConsultationDto> getConsultationsByUserIdAndStatus(Long userId, Consultation.ConsultationStatus status);
    
    List<ConsultationDto> getConsultationsByDoctorIdAndStatus(Long doctorId, Consultation.ConsultationStatus status);
    
    List<ConsultationDto> getConsultationsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    List<ConsultationDto> getConsultationsByDoctorIdAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<ConsultationDto> getConsultationsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    ConsultationDto updateConsultation(Long id, ConsultationDto consultationDto);
    
    void deleteConsultation(Long id);
    
    void updateConsultationStatus(Long id, Consultation.ConsultationStatus status);
    
    void updateConsultationDiagnosis(Long id, String diagnosis, String prescription, String notes);
    
    List<ConsultationDto> getOverdueConsultations();
    
    void generateMeetingLink(Long consultationId);
}
