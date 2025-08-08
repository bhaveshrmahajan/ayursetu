package com.ayursetu.pharmacy.repositories;

import com.ayursetu.pharmacy.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
    List<Medicine> findByCategory(String category);
    
    List<Medicine> findByIsAvailableTrue();
    
    List<Medicine> findByRequiresPrescription(Boolean requiresPrescription);
    
    List<Medicine> findByNameContainingIgnoreCase(String name);
    
    List<Medicine> findByGenericNameContainingIgnoreCase(String genericName);
    
    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity > 0")
    List<Medicine> findAvailableMedicines();
    
    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity <= 10")
    List<Medicine> findLowStockMedicines();
}
