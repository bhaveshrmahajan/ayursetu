package com.ayursetu.doctor.services;

import com.ayursetu.doctor.dto.SpecializationDto;
import com.ayursetu.doctor.entities.Specialization;
import com.ayursetu.doctor.exception.ResourceNotFoundException;
import com.ayursetu.doctor.repository.SpecializationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecializationServiceImpl implements SpecializationService {
    
    private final SpecializationRepository specializationRepository;
    private final ModelMapper modelMapper;
    
    @Override
    @Transactional
    public SpecializationDto createSpecialization(SpecializationDto specializationDto) {
        log.info("Creating new specialization: {}", specializationDto.getName());
        
        if (specializationRepository.existsByName(specializationDto.getName())) {
            throw new IllegalArgumentException("Specialization with name '" + specializationDto.getName() + "' already exists");
        }
        
        Specialization specialization = modelMapper.map(specializationDto, Specialization.class);
        Specialization savedSpecialization = specializationRepository.save(specialization);
        
        log.info("Specialization created successfully with ID: {}", savedSpecialization.getId());
        return modelMapper.map(savedSpecialization, SpecializationDto.class);
    }
    
    @Override
    public SpecializationDto getSpecializationById(Long id) {
        log.info("Fetching specialization with ID: {}", id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with ID: " + id));
        return modelMapper.map(specialization, SpecializationDto.class);
    }
    
    @Override
    public SpecializationDto getSpecializationByName(String name) {
        log.info("Fetching specialization with name: {}", name);
        Specialization specialization = specializationRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with name: " + name));
        return modelMapper.map(specialization, SpecializationDto.class);
    }
    
    @Override
    public List<SpecializationDto> getAllSpecializations() {
        log.info("Fetching all specializations");
        return specializationRepository.findAll().stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SpecializationDto> getActiveSpecializations() {
        log.info("Fetching active specializations");
        return specializationRepository.findByIsActiveTrue().stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SpecializationDto> getSpecializationsByCategory(String category) {
        log.info("Fetching specializations by category: {}", category);
        return specializationRepository.findByCategory(category).stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<SpecializationDto> searchSpecializations(String keyword) {
        log.info("Searching specializations with keyword: {}", keyword);
        return specializationRepository.searchByKeyword(keyword).stream()
                .map(specialization -> modelMapper.map(specialization, SpecializationDto.class))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<String> getAllCategories() {
        log.info("Fetching all categories");
        return specializationRepository.findAllActiveCategories();
    }
    
    @Override
    @Transactional
    public SpecializationDto updateSpecialization(Long id, SpecializationDto specializationDto) {
        log.info("Updating specialization with ID: {}", id);
        Specialization existingSpecialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with ID: " + id));
        
        modelMapper.map(specializationDto, existingSpecialization);
        existingSpecialization.setId(id); // Ensure ID is not overwritten
        
        Specialization updatedSpecialization = specializationRepository.save(existingSpecialization);
        
        log.info("Specialization updated successfully with ID: {}", updatedSpecialization.getId());
        return modelMapper.map(updatedSpecialization, SpecializationDto.class);
    }
    
    @Override
    @Transactional
    public void deleteSpecialization(Long id) {
        log.info("Deleting specialization with ID: {}", id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with ID: " + id));
        
        specializationRepository.delete(specialization);
        log.info("Specialization deleted successfully with ID: {}", id);
    }
    
    @Override
    @Transactional
    public void activateSpecialization(Long id) {
        log.info("Activating specialization with ID: {}", id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with ID: " + id));
        
        specialization.setIsActive(true);
        specializationRepository.save(specialization);
        log.info("Specialization activated successfully with ID: {}", id);
    }
    
    @Override
    @Transactional
    public void deactivateSpecialization(Long id) {
        log.info("Deactivating specialization with ID: {}", id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found with ID: " + id));
        
        specialization.setIsActive(false);
        specializationRepository.save(specialization);
        log.info("Specialization deactivated successfully with ID: {}", id);
    }
}




