package com.ayursetu.doctor.services;

import com.ayursetu.doctor.dto.SpecializationDto;

import java.util.List;

public interface SpecializationService {
    
    SpecializationDto createSpecialization(SpecializationDto specializationDto);
    
    SpecializationDto getSpecializationById(Long id);
    
    SpecializationDto getSpecializationByName(String name);
    
    List<SpecializationDto> getAllSpecializations();
    
    List<SpecializationDto> getActiveSpecializations();
    
    List<SpecializationDto> getSpecializationsByCategory(String category);
    
    List<SpecializationDto> searchSpecializations(String keyword);
    
    List<String> getAllCategories();
    
    SpecializationDto updateSpecialization(Long id, SpecializationDto specializationDto);
    
    void deleteSpecialization(Long id);
    
    void activateSpecialization(Long id);
    
    void deactivateSpecialization(Long id);
}




