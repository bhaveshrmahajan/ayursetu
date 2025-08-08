package com.userservice.dto;

import com.userservice.entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotBlank(message = "Name is required")
    @Size(min = 4 , max = 20, message = "Name must be minimum 4 character and maximum 20 character")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Email is required")
	@Size(min = 8 , message = "Password must be minimum 8 character")
	private String password;
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Role is required")
    private Role role;
}
