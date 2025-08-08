package com.ayursetu.pharmacy.services;

import com.ayursetu.pharmacy.dto.MedicineDto;
import com.ayursetu.pharmacy.entities.Medicine;

import java.util.List;

public interface PharmacyService {
    
    List<MedicineDto> getAllMedicines();
    
    MedicineDto getMedicineById(Long id);
    
    MedicineDto createMedicine(MedicineDto medicineDto);
    
    MedicineDto updateMedicine(Long id, MedicineDto medicineDto);
    
    void deleteMedicine(Long id);
    
    List<MedicineDto> getMedicinesByCategory(String category);
    
    List<MedicineDto> getAvailableMedicines();
    
    List<MedicineDto> searchMedicines(String query);
    
    void updateStock(Long medicineId, Integer quantity);
    
    List<MedicineDto> getLowStockMedicines();
}
