package com.ayursetu.pharmacy.services;

import com.ayursetu.pharmacy.dto.MedicineDto;
import com.ayursetu.pharmacy.entities.Medicine;
import com.ayursetu.pharmacy.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyServiceImpl implements PharmacyService {
    
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    @Override
    public List<MedicineDto> getAllMedicines() {
        log.info("Fetching all medicines");
        return medicineRepository.findAll().stream()
                .map(medicine -> modelMapper.map(medicine, MedicineDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public MedicineDto getMedicineById(Long id) {
        log.info("Fetching medicine with id: {}", id);
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        return modelMapper.map(medicine, MedicineDto.class);
    }
    
    @Override
    @Transactional
    public MedicineDto createMedicine(MedicineDto medicineDto) {
        log.info("Creating new medicine: {}", medicineDto.getName());
        Medicine medicine = modelMapper.map(medicineDto, Medicine.class);
        Medicine savedMedicine = medicineRepository.save(medicine);
        
        // Publish medicine created event
        try {
            kafkaTemplate.send("medicine-events", "medicine-created", "Medicine created: " + savedMedicine.getId());
            log.info("Kafka medicine created event sent for medicine: {}", savedMedicine.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka medicine created event for medicine: {}. Error: {}", savedMedicine.getId(), e.getMessage());
        }
        
        return modelMapper.map(savedMedicine, MedicineDto.class);
    }
    
    @Override
    @Transactional
    public MedicineDto updateMedicine(Long id, MedicineDto medicineDto) {
        log.info("Updating medicine with id: {}", id);
        Medicine existingMedicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        
        // Update fields
        existingMedicine.setName(medicineDto.getName());
        existingMedicine.setGenericName(medicineDto.getGenericName());
        existingMedicine.setManufacturer(medicineDto.getManufacturer());
        existingMedicine.setCategory(medicineDto.getCategory());
        existingMedicine.setDosageForm(medicineDto.getDosageForm());
        existingMedicine.setStrength(medicineDto.getStrength());
        existingMedicine.setDescription(medicineDto.getDescription());
        existingMedicine.setSideEffects(medicineDto.getSideEffects());
        existingMedicine.setContraindications(medicineDto.getContraindications());
        existingMedicine.setPrice(medicineDto.getPrice());
        existingMedicine.setStockQuantity(medicineDto.getStockQuantity());
        existingMedicine.setRequiresPrescription(medicineDto.getRequiresPrescription());
        existingMedicine.setIsAvailable(medicineDto.getIsAvailable());
        existingMedicine.setImageUrl(medicineDto.getImageUrl());
        
        Medicine updatedMedicine = medicineRepository.save(existingMedicine);
        
        // Publish medicine updated event
        try {
            kafkaTemplate.send("medicine-events", "medicine-updated", "Medicine updated: " + updatedMedicine.getId());
            log.info("Kafka medicine updated event sent for medicine: {}", updatedMedicine.getId());
        } catch (Exception e) {
            log.warn("Failed to send Kafka medicine updated event for medicine: {}. Error: {}", updatedMedicine.getId(), e.getMessage());
        }
        
        return modelMapper.map(updatedMedicine, MedicineDto.class);
    }
    
    @Override
    @Transactional
    public void deleteMedicine(Long id) {
        log.info("Deleting medicine with id: {}", id);
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));
        
        medicineRepository.delete(medicine);
        
        // Publish medicine deleted event
        try {
            kafkaTemplate.send("medicine-events", "medicine-deleted", "Medicine deleted: " + id);
            log.info("Kafka medicine deleted event sent for medicine: {}", id);
        } catch (Exception e) {
            log.warn("Failed to send Kafka medicine deleted event for medicine: {}. Error: {}", id, e.getMessage());
        }
    }
    
    @Override
    public List<MedicineDto> getMedicinesByCategory(String category) {
        log.info("Fetching medicines by category: {}", category);
        return medicineRepository.findByCategory(category).stream()
                .map(medicine -> modelMapper.map(medicine, MedicineDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicineDto> getAvailableMedicines() {
        log.info("Fetching available medicines");
        return medicineRepository.findAvailableMedicines().stream()
                .map(medicine -> modelMapper.map(medicine, MedicineDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MedicineDto> searchMedicines(String query) {
        log.info("Searching medicines with query: {}", query);
        List<Medicine> medicines = medicineRepository.findByNameContainingIgnoreCase(query);
        medicines.addAll(medicineRepository.findByGenericNameContainingIgnoreCase(query));
        
        return medicines.stream()
                .distinct()
                .map(medicine -> modelMapper.map(medicine, MedicineDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void updateStock(Long medicineId, Integer quantity) {
        log.info("Updating stock for medicine: {} with quantity: {}", medicineId, quantity);
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found with id: " + medicineId));
        
        medicine.setStockQuantity(medicine.getStockQuantity() + quantity);
        medicineRepository.save(medicine);
        
        // Publish stock updated event
        try {
            kafkaTemplate.send("stock-events", "stock-updated", "Stock updated for medicine: " + medicineId);
            log.info("Kafka stock updated event sent for medicine: {}", medicineId);
        } catch (Exception e) {
            log.warn("Failed to send Kafka stock updated event for medicine: {}. Error: {}", medicineId, e.getMessage());
        }
    }
    
    @Override
    public List<MedicineDto> getLowStockMedicines() {
        log.info("Fetching low stock medicines");
        return medicineRepository.findLowStockMedicines().stream()
                .map(medicine -> modelMapper.map(medicine, MedicineDto.class))
                .collect(Collectors.toList());
    }
}
