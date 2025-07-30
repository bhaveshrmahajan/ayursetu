package com.userservice.dto;

import com.userservice.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {
    
    @Size(min = 4, max = 20, message = "Name must be minimum 4 character and maximum 20 character")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Size(min = 8, message = "Password must be minimum 8 character")
    private String password;
    
    private Role role;
} 