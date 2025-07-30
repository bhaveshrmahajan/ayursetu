package com.doctorservice.services;

import com.doctorservice.dto.*;
import com.doctorservice.entities.*;
import com.doctorservice.exception.DoctorAlreadyExistsException;
import com.doctorservice.exception.ResourceNotFoundException;
import com.doctorservice.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final AvailabilityRepository availabilityRepository;
    private final DoctorReviewRepository doctorReviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public DoctorDto registerDoctor(DoctorRegistrationDto registrationDto) {
        log.info("Registering new doctor with email: {}", registrationDto.getEmail());
        
        // Check if doctor already exists
        if (doctorRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new DoctorAlreadyExistsException("Doctor with email " + registrationDto.getEmail() + " already exists");
        }
        
        // Get specialization
        Specialization specialization = specializationRepository.findById(registrationDto.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));
        
        // Create doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(registrationDto.getName());
        doctor.setEmail(registrationDto.getEmail());
        doctor.setPhone(registrationDto.getPhone());
        doctor.setLicenseNumber(registrationDto.getLicenseNumber());
        doctor.setQualification(registrationDto.getQualification());
        doctor.setExperienceYears(registrationDto.getExperienceYears());
        doctor.setConsultationFee(registrationDto.getConsultationFee());
        doctor.setBio(registrationDto.getBio());
        doctor.setSpecialization(specialization);
        doctor.setStatus(DoctorStatus.PENDING_APPROVAL);
        
        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor registered successfully with ID: {}", savedDoctor.getId());
        
        return modelMapper.map(savedDoctor, DoctorDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDto getDoctorById(Long id) {
        log.info("Fetching doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        return modelMapper.map(doctor, DoctorDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorDto getDoctorByEmail(String email) {
        log.info("Fetching doctor with email: {}", email);
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with email: " + email));
        return modelMapper.map(doctor, DoctorDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDto> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll().stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDto> getDoctorsBySpecialization(Long specializationId) {
        log.info("Fetching doctors by specialization ID: {}", specializationId);
        return doctorRepository.findBySpecializationIdAndStatus(specializationId, DoctorStatus.ACTIVE).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorDto> searchDoctors(String searchTerm) {
        log.info("Searching doctors with term: {}", searchTerm);
        return doctorRepository.searchDoctors(searchTerm).stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDto updateDoctorStatus(Long id, String status) {
        log.info("Updating doctor status for ID: {} to: {}", id, status);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        
        doctor.setStatus(DoctorStatus.valueOf(status.toUpperCase()));
        Doctor updatedDoctor = doctorRepository.save(doctor);
        
        return modelMapper.map(updatedDoctor, DoctorDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecializationDto> getAllSpecializations() {
        log.info("Fetching all specializations");
        return specializationRepository.findAll().stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecializationDto> getActiveSpecializations() {
        log.info("Fetching active specializations");
        return specializationRepository.findByActiveTrue().stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SpecializationDto createSpecialization(SpecializationDto specializationDto) {
        log.info("Creating new specialization: {}", specializationDto.getName());
        Specialization specialization = modelMapper.map(specializationDto, Specialization.class);
        Specialization savedSpecialization = specializationRepository.save(specialization);
        return modelMapper.map(savedSpecialization, SpecializationDto.class);
    }

    @Override
    public DoctorReviewDto addReview(DoctorReviewDto reviewDto) {
        log.info("Adding review for doctor ID: {}", reviewDto.getDoctorId());
        Doctor doctor = doctorRepository.findById(reviewDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        
        DoctorReview review = modelMapper.map(reviewDto, DoctorReview.class);
        review.setDoctor(doctor);
        DoctorReview savedReview = doctorReviewRepository.save(review);
        
        // Update doctor rating
        updateDoctorRating(doctor.getId());
        
        return modelMapper.map(savedReview, DoctorReviewDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorReviewDto> getDoctorReviews(Long doctorId) {
        log.info("Fetching reviews for doctor ID: {}", doctorId);
        return doctorReviewRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId).stream()
                .map(review -> modelMapper.map(review, DoctorReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double getDoctorRating(Long doctorId) {
        log.info("Fetching rating for doctor ID: {}", doctorId);
        return doctorReviewRepository.getAverageRatingByDoctorId(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getDoctorTotalReviews(Long doctorId) {
        log.info("Fetching total reviews for doctor ID: {}", doctorId);
        return doctorReviewRepository.getTotalReviewsByDoctorId(doctorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AvailabilityDto> getDoctorAvailability(Long doctorId) {
        log.info("Fetching availability for doctor ID: {}", doctorId);
        return availabilityRepository.findByDoctorId(doctorId).stream()
                .map(availability -> modelMapper.map(availability, AvailabilityDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AvailabilityDto addAvailability(AvailabilityDto availabilityDto) {
        log.info("Adding availability for doctor ID: {}", availabilityDto.getDoctorId());
        Doctor doctor = doctorRepository.findById(availabilityDto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
        
        Availability availability = modelMapper.map(availabilityDto, Availability.class);
        availability.setDoctor(doctor);
        Availability savedAvailability = availabilityRepository.save(availability);
        
        return modelMapper.map(savedAvailability, AvailabilityDto.class);
    }

    @Override
    public void deleteAvailability(Long availabilityId) {
        log.info("Deleting availability with ID: {}", availabilityId);
        availabilityRepository.deleteById(availabilityId);
    }

    private void updateDoctorRating(Long doctorId) {
        Double averageRating = doctorReviewRepository.getAverageRatingByDoctorId(doctorId);
        Integer totalReviews = doctorReviewRepository.getTotalReviewsByDoctorId(doctorId);
        
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            doctor.setRating(averageRating != null ? averageRating : 0.0);
            doctor.setTotalReviews(totalReviews != null ? totalReviews : 0);
            doctorRepository.save(doctor);
        }
    }
} 