package com.ayursetu.pharmacy.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDto {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;
    
    @NotBlank(message = "Generic name is required")
    @Size(max = 100, message = "Generic name must be less than 100 characters")
    private String genericName;
    
    @NotBlank(message = "Manufacturer is required")
    @Size(max = 100, message = "Manufacturer must be less than 100 characters")
    private String manufacturer;
    
    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;
    
    @NotBlank(message = "Dosage form is required")
    @Size(max = 50, message = "Dosage form must be less than 50 characters")
    private String dosageForm;
    
    @NotBlank(message = "Strength is required")
    @Size(max = 50, message = "Strength must be less than 50 characters")
    private String strength;
    
    private String description;
    
    private String sideEffects;
    
    private String contraindications;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity;
    
    @NotNull(message = "Requires prescription flag is required")
    private Boolean requiresPrescription;
    
    private Boolean isAvailable = true;
    
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
}
