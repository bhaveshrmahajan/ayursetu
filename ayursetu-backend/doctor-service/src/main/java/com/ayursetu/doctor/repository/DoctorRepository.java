package com.ayursetu.doctor.repository;

import com.ayursetu.doctor.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByEmail(String email);
    
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    
    List<Doctor> findBySpecialization(String specialization);
    
    List<Doctor> findByCity(String city);
    
    List<Doctor> findByIsAvailableTrue();
    
    List<Doctor> findByIsVerifiedTrue();
    
    @Query("SELECT d FROM Doctor d WHERE d.specialization = :specialization AND d.city = :city AND d.isAvailable = true")
    List<Doctor> findBySpecializationAndCity(@Param("specialization") String specialization, @Param("city") String city);
    
    @Query("SELECT d FROM Doctor d WHERE d.consultationFee BETWEEN :minFee AND :maxFee AND d.isAvailable = true")
    List<Doctor> findByConsultationFeeRange(@Param("minFee") Double minFee, @Param("maxFee") Double maxFee);
    
    @Query("SELECT DISTINCT d.specialization FROM Doctor d")
    List<String> findAllSpecializations();
    
    @Query("SELECT DISTINCT d.city FROM Doctor d")
    List<String> findAllCities();
}
