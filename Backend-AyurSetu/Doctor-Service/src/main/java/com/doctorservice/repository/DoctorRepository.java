package com.doctorservice.repository;

import com.doctorservice.entities.Doctor;
import com.doctorservice.entities.DoctorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    
    List<Doctor> findByStatus(DoctorStatus status);
    
    List<Doctor> findBySpecializationIdAndStatus(Long specializationId, DoctorStatus status);
    
    @Query("SELECT d FROM Doctor d WHERE d.specialization.id = :specializationId AND d.status = :status AND d.rating >= :minRating")
    List<Doctor> findBySpecializationAndStatusAndMinRating(@Param("specializationId") Long specializationId, 
                                                          @Param("status") DoctorStatus status, 
                                                          @Param("minRating") Double minRating);
    
    @Query("SELECT d FROM Doctor d WHERE d.name LIKE %:searchTerm% OR d.qualification LIKE %:searchTerm%")
    List<Doctor> searchDoctors(@Param("searchTerm") String searchTerm);
} 