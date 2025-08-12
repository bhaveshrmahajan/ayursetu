package com.ayursetu.doctor.controller;

import com.ayursetu.doctor.dto.SpecializationDto;
import com.ayursetu.doctor.services.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
@Slf4j
public class SpecializationController {
    
    private final SpecializationService specializationService;
    
    @PostMapping
    public ResponseEntity<SpecializationDto> createSpecialization(@Valid @RequestBody SpecializationDto specializationDto) {
        log.info("Creating new specialization");
        SpecializationDto createdSpecialization = specializationService.createSpecialization(specializationDto);
        return new ResponseEntity<>(createdSpecialization, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SpecializationDto> getSpecializationById(@PathVariable Long id) {
        log.info("Fetching specialization with ID: {}", id);
        SpecializationDto specialization = specializationService.getSpecializationById(id);
        return ResponseEntity.ok(specialization);
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<SpecializationDto> getSpecializationByName(@PathVariable String name) {
        log.info("Fetching specialization with name: {}", name);
        SpecializationDto specialization = specializationService.getSpecializationByName(name);
        return ResponseEntity.ok(specialization);
    }
    
    @GetMapping
    public ResponseEntity<List<SpecializationDto>> getAllSpecializations() {
        log.info("Fetching all specializations");
        List<SpecializationDto> specializations = specializationService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<SpecializationDto>> getActiveSpecializations() {
        log.info("Fetching active specializations");
        List<SpecializationDto> specializations = specializationService.getActiveSpecializations();
        return ResponseEntity.ok(specializations);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SpecializationDto>> getSpecializationsByCategory(@PathVariable String category) {
        log.info("Fetching specializations by category: {}", category);
        List<SpecializationDto> specializations = specializationService.getSpecializationsByCategory(category);
        return ResponseEntity.ok(specializations);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<SpecializationDto>> searchSpecializations(@RequestParam String keyword) {
        log.info("Searching specializations with keyword: {}", keyword);
        List<SpecializationDto> specializations = specializationService.searchSpecializations(keyword);
        return ResponseEntity.ok(specializations);
    }
    
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        log.info("Fetching all categories");
        List<String> categories = specializationService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SpecializationDto> updateSpecialization(@PathVariable Long id, @Valid @RequestBody SpecializationDto specializationDto) {
        log.info("Updating specialization with ID: {}", id);
        SpecializationDto updatedSpecialization = specializationService.updateSpecialization(id, specializationDto);
        return ResponseEntity.ok(updatedSpecialization);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        log.info("Deleting specialization with ID: {}", id);
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateSpecialization(@PathVariable Long id) {
        log.info("Activating specialization with ID: {}", id);
        specializationService.activateSpecialization(id);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateSpecialization(@PathVariable Long id) {
        log.info("Deactivating specialization with ID: {}", id);
        specializationService.deactivateSpecialization(id);
        return ResponseEntity.ok().build();
    }
}




