package com.ayursetu.consultation.services;

import com.ayursetu.consultation.dto.ConsultationDto;
import com.ayursetu.consultation.dto.DoctorInfo;
import com.ayursetu.consultation.entities.Consultation;
import com.ayursetu.consultation.exception.ResourceNotFoundException;
import com.ayursetu.consultation.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultationServiceImpl implements ConsultationService {
    
    private final ConsultationRepository consultationRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RestTemplate restTemplate;
    
    @Override
    public ConsultationDto createConsultation(ConsultationDto consultationDto) {
        log.info("Creating new consultation for user: {} with doctor: {}", consultationDto.getUserId(), consultationDto.getDoctorId());
        
        // Fetch doctor details to get consultation fee
        DoctorInfo doctor = fetchDoctorDetails(consultationDto.getDoctorId());
        if (doctor == null) {
            log.warn("Doctor service not available, using default consultation fee for doctor ID: {}", consultationDto.getDoctorId());
            // Use default consultation fee if doctor service is not available
            if (consultationDto.getConsultationFee() == null) {
                consultationDto.setConsultationFee(100.0); // Default fee
            }
        } else {
            // Set consultation fee from doctor service
            consultationDto.setConsultationFee(doctor.getConsultationFee());
        }
        
        Consultation consultation = modelMapper.map(consultationDto, Consultation.class);
        consultation.setMeetingLink(generateMeetingLink());
        
        Consultation savedConsultation = consultationRepository.save(consultation);
        
        // Send notification via Kafka (with error handling)
        try {
            kafkaTemplate.send("consultation-events", "Consultation created: " + savedConsultation.getId());
            log.info("Kafka notification sent for consultation: {}", savedConsultation.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification for consultation: {}. Error: {}", savedConsultation.getId(), e.getMessage());
        }
        
        log.info("Consultation created successfully with ID: {}", savedConsultation.getId());
        return modelMapper.map(savedConsultation, ConsultationDto.class);
    }
    
    @Override
    public ConsultationDto getConsultationById(Long id) {
        log.info("Fetching consultation with ID: {}", id);
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with ID: " + id));
        return modelMapper.map(consultation, ConsultationDto.class);
    }
    
    @Override
    public List<ConsultationDto> getAllConsultations() {
        log.info("Fetching all consultations");
        return consultationRepository.findAll().stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByUserId(Long userId) {
        log.info("Fetching consultations for user: {}", userId);
        return consultationRepository.findByUserId(userId).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByDoctorId(Long doctorId) {
        log.info("Fetching consultations for doctor: {}", doctorId);
        return consultationRepository.findByDoctorId(doctorId).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByStatus(Consultation.ConsultationStatus status) {
        log.info("Fetching consultations by status: {}", status);
        return consultationRepository.findByStatus(status).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByUserIdAndStatus(Long userId, Consultation.ConsultationStatus status) {
        log.info("Fetching consultations for user: {} with status: {}", userId, status);
        return consultationRepository.findByUserIdAndStatus(userId, status).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByDoctorIdAndStatus(Long doctorId, Consultation.ConsultationStatus status) {
        log.info("Fetching consultations for doctor: {} with status: {}", doctorId, status);
        return consultationRepository.findByDoctorIdAndStatus(doctorId, status).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching consultations between {} and {}", startDate, endDate);
        return consultationRepository.findByAppointmentDateTimeBetween(startDate, endDate).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByDoctorIdAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching consultations for doctor: {} between {} and {}", doctorId, startDate, endDate);
        return consultationRepository.findByDoctorIdAndAppointmentDateTimeBetween(doctorId, startDate, endDate).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ConsultationDto> getConsultationsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching consultations for user: {} between {} and {}", userId, startDate, endDate);
        return consultationRepository.findByUserIdAndAppointmentDateTimeBetween(userId, startDate, endDate).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public ConsultationDto updateConsultation(Long id, ConsultationDto consultationDto) {
        log.info("Updating consultation with ID: {}", id);
        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with ID: " + id));
        
        // Update fields
        existingConsultation.setAppointmentDateTime(consultationDto.getAppointmentDateTime());
        existingConsultation.setStatus(consultationDto.getStatus());
        existingConsultation.setType(consultationDto.getType());
        existingConsultation.setSymptoms(consultationDto.getSymptoms());
        existingConsultation.setDiagnosis(consultationDto.getDiagnosis());
        existingConsultation.setPrescription(consultationDto.getPrescription());
        existingConsultation.setNotes(consultationDto.getNotes());
        // Consultation fee is managed by doctor service, not stored here
        
        Consultation updatedConsultation = consultationRepository.save(existingConsultation);
        
        // Send notification via Kafka (with error handling)
        try {
            kafkaTemplate.send("consultation-events", "Consultation updated: " + updatedConsultation.getId());
            log.info("Kafka notification sent for consultation update: {}", updatedConsultation.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification for consultation update: {}. Error: {}", updatedConsultation.getId(), e.getMessage());
        }
        
        log.info("Consultation updated successfully with ID: {}", updatedConsultation.getId());
        return modelMapper.map(updatedConsultation, ConsultationDto.class);
    }
    
    @Override
    public void deleteConsultation(Long id) {
        log.info("Deleting consultation with ID: {}", id);
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with ID: " + id));
        
        consultationRepository.delete(consultation);
        
        // Send notification via Kafka (with error handling)
        try {
            kafkaTemplate.send("consultation-events", "Consultation deleted: " + consultation.getId());
            log.info("Kafka notification sent for consultation deletion: {}", consultation.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification for consultation deletion: {}. Error: {}", consultation.getId(), e.getMessage());
        }
        
        log.info("Consultation deleted successfully with ID: {}", id);
    }
    
    @Override
    public void updateConsultationStatus(Long id, Consultation.ConsultationStatus status) {
        log.info("Updating consultation status for ID: {} to {}", id, status);
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with ID: " + id));
        
        consultation.setStatus(status);
        consultationRepository.save(consultation);
        
        // Send notification via Kafka (with error handling)
        try {
            kafkaTemplate.send("consultation-events", "Consultation status updated: " + consultation.getId() + " - " + status);
            log.info("Kafka notification sent for consultation status update: {}", consultation.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification for consultation status update: {}. Error: {}", consultation.getId(), e.getMessage());
        }
        
        log.info("Consultation status updated successfully for ID: {}", id);
    }
    
    @Override
    public void updateConsultationDiagnosis(Long id, String diagnosis, String prescription, String notes) {
        log.info("Updating consultation diagnosis for ID: {}", id);
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with ID: " + id));
        
        consultation.setDiagnosis(diagnosis);
        consultation.setPrescription(prescription);
        consultation.setNotes(notes);
        consultation.setStatus(Consultation.ConsultationStatus.COMPLETED);
        
        consultationRepository.save(consultation);
        
        // Send notification via Kafka (with error handling)
        try {
            kafkaTemplate.send("consultation-events", "Consultation diagnosis updated: " + consultation.getId());
            log.info("Kafka notification sent for consultation diagnosis update: {}", consultation.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification for consultation diagnosis update: {}. Error: {}", consultation.getId(), e.getMessage());
        }
        
        log.info("Consultation diagnosis updated successfully for ID: {}", id);
    }
    
    @Override
    public List<ConsultationDto> getOverdueConsultations() {
        log.info("Fetching overdue consultations");
        return consultationRepository.findOverdueConsultations(LocalDateTime.now()).stream()
                .map(consultation -> modelMapper.map(consultation, ConsultationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public void generateMeetingLink(Long consultationId) {
        log.info("Generating meeting link for consultation: {}", consultationId);
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation not found with ID: " + consultationId));
        
        consultation.setMeetingLink(generateMeetingLink());
        consultationRepository.save(consultation);
        
        // Send notification via Kafka (with error handling)
        try {
            kafkaTemplate.send("consultation-events", "Meeting link generated: " + consultation.getId());
            log.info("Kafka notification sent for meeting link generation: {}", consultation.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka notification for meeting link generation: {}. Error: {}", consultation.getId(), e.getMessage());
        }
        
        log.info("Meeting link generated successfully for consultation: {}", consultationId);
    }
    
    private String generateMeetingLink() {
        return "https://meet.ayursetu.com/" + UUID.randomUUID().toString();
    }
    
    // Helper method to fetch doctor details from doctor service
    private DoctorInfo fetchDoctorDetails(Long doctorId) {
        try {
            String doctorServiceUrl = "http://doctor-service:8082/api/doctors/" + doctorId;
            return restTemplate.getForObject(doctorServiceUrl, DoctorInfo.class);
        } catch (Exception e) {
            log.error("Failed to fetch doctor details for ID: {}", doctorId, e);
            return null;
        }
    }
}
