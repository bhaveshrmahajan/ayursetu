package com.ayursetu.pharmacy.controller;

import com.ayursetu.pharmacy.dto.MedicineDto;
import com.ayursetu.pharmacy.services.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PharmacyController {
    
    private final PharmacyService pharmacyService;
    
    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Pharmacy Service is running!");
    }
    
    // Medicine endpoints
    @GetMapping("/medicines")
    public ResponseEntity<List<MedicineDto>> getAllMedicines() {
        log.info("GET /api/pharmacy/medicines - Fetching all medicines");
        List<MedicineDto> medicines = pharmacyService.getAllMedicines();
        return ResponseEntity.ok(medicines);
    }
    
    @GetMapping("/medicines/{id}")
    public ResponseEntity<MedicineDto> getMedicineById(@PathVariable Long id) {
        log.info("GET /api/pharmacy/medicines/{} - Fetching medicine by id", id);
        MedicineDto medicine = pharmacyService.getMedicineById(id);
        return ResponseEntity.ok(medicine);
    }
    
    @PostMapping("/medicines")
    public ResponseEntity<MedicineDto> createMedicine(@Valid @RequestBody MedicineDto medicineDto) {
        log.info("POST /api/pharmacy/medicines - Creating new medicine: {}", medicineDto.getName());
        MedicineDto createdMedicine = pharmacyService.createMedicine(medicineDto);
        return ResponseEntity.ok(createdMedicine);
    }
    
    @PutMapping("/medicines/{id}")
    public ResponseEntity<MedicineDto> updateMedicine(@PathVariable Long id, @Valid @RequestBody MedicineDto medicineDto) {
        log.info("PUT /api/pharmacy/medicines/{} - Updating medicine", id);
        MedicineDto updatedMedicine = pharmacyService.updateMedicine(id, medicineDto);
        return ResponseEntity.ok(updatedMedicine);
    }
    
    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.info("DELETE /api/pharmacy/medicines/{} - Deleting medicine", id);
        pharmacyService.deleteMedicine(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/medicines/category/{category}")
    public ResponseEntity<List<MedicineDto>> getMedicinesByCategory(@PathVariable String category) {
        log.info("GET /api/pharmacy/medicines/category/{} - Fetching medicines by category", category);
        List<MedicineDto> medicines = pharmacyService.getMedicinesByCategory(category);
        return ResponseEntity.ok(medicines);
    }
    
    @GetMapping("/medicines/available")
    public ResponseEntity<List<MedicineDto>> getAvailableMedicines() {
        log.info("GET /api/pharmacy/medicines/available - Fetching available medicines");
        List<MedicineDto> medicines = pharmacyService.getAvailableMedicines();
        return ResponseEntity.ok(medicines);
    }
    
    @GetMapping("/medicines/search")
    public ResponseEntity<List<MedicineDto>> searchMedicines(@RequestParam String query) {
        log.info("GET /api/pharmacy/medicines/search?query={} - Searching medicines", query);
        List<MedicineDto> medicines = pharmacyService.searchMedicines(query);
        return ResponseEntity.ok(medicines);
    }
    
    @PutMapping("/medicines/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        log.info("PUT /api/pharmacy/medicines/{}/stock?quantity={} - Updating stock", id, quantity);
        pharmacyService.updateStock(id, quantity);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/medicines/low-stock")
    public ResponseEntity<List<MedicineDto>> getLowStockMedicines() {
        log.info("GET /api/pharmacy/medicines/low-stock - Fetching low stock medicines");
        List<MedicineDto> medicines = pharmacyService.getLowStockMedicines();
        return ResponseEntity.ok(medicines);
    }
}
