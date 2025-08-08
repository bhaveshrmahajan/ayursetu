package com.ayursetu.doctor.services;

import com.ayursetu.doctor.dto.DoctorDto;
import com.ayursetu.doctor.entities.Doctor;
import com.ayursetu.doctor.exception.DoctorAlreadyExistsException;
import com.ayursetu.doctor.exception.ResourceNotFoundException;
import com.ayursetu.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    
    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto) {
        log.info("Creating new doctor with email: {}", doctorDto.getEmail());
        
        // Check if doctor already exists
        if (doctorRepository.findByEmail(doctorDto.getEmail()).isPresent()) {
            throw new DoctorAlreadyExistsException("Doctor with email " + doctorDto.getEmail() + " already exists");
        }
        
        if (doctorRepository.findByLicenseNumber(doctorDto.getLicenseNumber()).isPresent()) {
            throw new DoctorAlreadyExistsException("Doctor with license number " + doctorDto.getLicenseNumber() + " already exists");
        }
        
        // Create doctor entity manually to avoid ModelMapper issues with specializations
        Doctor doctor = new Doctor();
        doctor.setName(doctorDto.getName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhone(doctorDto.getPhone());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setQualification(doctorDto.getQualification());
        doctor.setLicenseNumber(doctorDto.getLicenseNumber());
        doctor.setExperience(doctorDto.getExperience());
        doctor.setBio(doctorDto.getBio());
        doctor.setAddress(doctorDto.getAddress());
        doctor.setCity(doctorDto.getCity());
        doctor.setState(doctorDto.getState());
        doctor.setCountry(doctorDto.getCountry());
        doctor.setPincode(doctorDto.getPincode());
        doctor.setConsultationFee(doctorDto.getConsultationFee());
        doctor.setIsAvailable(doctorDto.getIsAvailable() != null ? doctorDto.getIsAvailable() : true);
        doctor.setIsVerified(doctorDto.getIsVerified() != null ? doctorDto.getIsVerified() : false);
        doctor.setLanguages(doctorDto.getLanguages());
        
        Doctor savedDoctor = doctorRepository.save(doctor);
        
        // Send notification via Kafka
        kafkaTemplate.send("doctor-events", "Doctor created: " + savedDoctor.getEmail());
        
        log.info("Doctor created successfully with ID: {}", savedDoctor.getId());
        return modelMapper.map(savedDoctor, DoctorDto.class);
    }
    
    @Override
    public DoctorDto getDoctorById(Long id) {
        log.info("Fetching doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        return modelMapper.map(doctor, DoctorDto.class);
    }
    
    @Override
    public DoctorDto getDoctorByEmail(String email) {
        log.info("Fetching doctor with email: {}", email);
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with email: " + email));
        return modelMapper.map(doctor, DoctorDto.class);
    }
    
    @Override
    public List<DoctorDto> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DoctorDto> getDoctorsBySpecialization(String specialization) {
        log.info("Fetching doctors by specialization: {}", specialization);
        return doctorRepository.findBySpecialization(specialization).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DoctorDto> getDoctorsByCity(String city) {
        log.info("Fetching doctors by city: {}", city);
        return doctorRepository.findByCity(city).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DoctorDto> getAvailableDoctors() {
        log.info("Fetching available doctors");
        return doctorRepository.findByIsAvailableTrue().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DoctorDto> getVerifiedDoctors() {
        log.info("Fetching verified doctors");
        return doctorRepository.findByIsVerifiedTrue().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DoctorDto> getDoctorsBySpecializationAndCity(String specialization, String city) {
        log.info("Fetching doctors by specialization: {} and city: {}", specialization, city);
        return doctorRepository.findBySpecializationAndCity(specialization, city).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<DoctorDto> getDoctorsByFeeRange(Double minFee, Double maxFee) {
        log.info("Fetching doctors by fee range: {} - {}", minFee, maxFee);
        return doctorRepository.findByConsultationFeeRange(minFee, maxFee).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getAllSpecializations() {
        log.info("Fetching all specializations");
        return doctorRepository.findAllSpecializations();
    }
    
    @Override
    public List<String> getAllCities() {
        log.info("Fetching all cities");
        return doctorRepository.findAllCities();
    }
    
    @Override
    public DoctorDto updateDoctor(Long id, DoctorDto doctorDto) {
        log.info("Updating doctor with ID: {}", id);
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        
        // Update fields
        existingDoctor.setName(doctorDto.getName());
        existingDoctor.setPhone(doctorDto.getPhone());
        existingDoctor.setSpecialization(doctorDto.getSpecialization());
        existingDoctor.setQualification(doctorDto.getQualification());
        existingDoctor.setExperience(doctorDto.getExperience());
        existingDoctor.setBio(doctorDto.getBio());
        existingDoctor.setAddress(doctorDto.getAddress());
        existingDoctor.setCity(doctorDto.getCity());
        existingDoctor.setState(doctorDto.getState());
        existingDoctor.setCountry(doctorDto.getCountry());
        existingDoctor.setPincode(doctorDto.getPincode());
        existingDoctor.setConsultationFee(doctorDto.getConsultationFee());
        // Specializations will be handled separately through specialization service
        // existingDoctor.setSpecializations(doctorDto.getSpecializations());
        existingDoctor.setLanguages(doctorDto.getLanguages());
        
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        
        // Send notification via Kafka
        kafkaTemplate.send("doctor-events", "Doctor updated: " + updatedDoctor.getEmail());
        
        log.info("Doctor updated successfully with ID: {}", updatedDoctor.getId());
        return modelMapper.map(updatedDoctor, DoctorDto.class);
    }
    
    @Override
    public void deleteDoctor(Long id) {
        log.info("Deleting doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        
        doctorRepository.delete(doctor);
        
        // Send notification via Kafka
        kafkaTemplate.send("doctor-events", "Doctor deleted: " + doctor.getEmail());
        
        log.info("Doctor deleted successfully with ID: {}", id);
    }
    
    @Override
    public void updateAvailability(Long id, Boolean isAvailable) {
        log.info("Updating availability for doctor with ID: {} to {}", id, isAvailable);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        
        doctor.setIsAvailable(isAvailable);
        doctorRepository.save(doctor);
        
        // Send notification via Kafka
        kafkaTemplate.send("doctor-events", "Doctor availability updated: " + doctor.getEmail() + " - " + isAvailable);
        
        log.info("Doctor availability updated successfully for ID: {}", id);
    }
    
    @Override
    public void updateVerificationStatus(Long id, Boolean isVerified) {
        log.info("Updating verification status for doctor with ID: {} to {}", id, isVerified);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        
        doctor.setIsVerified(isVerified);
        doctorRepository.save(doctor);
        
        // Send notification via Kafka
        kafkaTemplate.send("doctor-events", "Doctor verification updated: " + doctor.getEmail() + " - " + isVerified);
        
        log.info("Doctor verification status updated successfully for ID: {}", id);
    }
}
