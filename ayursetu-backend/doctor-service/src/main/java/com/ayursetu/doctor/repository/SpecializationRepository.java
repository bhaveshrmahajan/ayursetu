package com.ayursetu.doctor.repository;

import com.ayursetu.doctor.entities.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    
    Optional<Specialization> findByName(String name);
    
    List<Specialization> findByCategory(String category);
    
    List<Specialization> findByIsActiveTrue();
    
    List<Specialization> findByIsActive(Boolean isActive);
    
    @Query("SELECT s FROM Specialization s WHERE s.name LIKE %:keyword% OR s.description LIKE %:keyword%")
    List<Specialization> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT DISTINCT s.category FROM Specialization s WHERE s.isActive = true")
    List<String> findAllActiveCategories();
    
    boolean existsByName(String name);
}




