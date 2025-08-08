package com.ayursetu.consultation.repository;

import com.ayursetu.consultation.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    
    List<Consultation> findByUserId(Long userId);
    
    List<Consultation> findByDoctorId(Long doctorId);
    
    List<Consultation> findByStatus(Consultation.ConsultationStatus status);
    
    List<Consultation> findByUserIdAndStatus(Long userId, Consultation.ConsultationStatus status);
    
    List<Consultation> findByDoctorIdAndStatus(Long doctorId, Consultation.ConsultationStatus status);
    
    @Query("SELECT c FROM Consultation c WHERE c.appointmentDateTime BETWEEN :startDate AND :endDate")
    List<Consultation> findByAppointmentDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c FROM Consultation c WHERE c.doctorId = :doctorId AND c.appointmentDateTime BETWEEN :startDate AND :endDate")
    List<Consultation> findByDoctorIdAndAppointmentDateTimeBetween(@Param("doctorId") Long doctorId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c FROM Consultation c WHERE c.userId = :userId AND c.appointmentDateTime BETWEEN :startDate AND :endDate")
    List<Consultation> findByUserIdAndAppointmentDateTimeBetween(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c FROM Consultation c WHERE c.status = 'SCHEDULED' AND c.appointmentDateTime < :currentTime")
    List<Consultation> findOverdueConsultations(@Param("currentTime") LocalDateTime currentTime);
}
