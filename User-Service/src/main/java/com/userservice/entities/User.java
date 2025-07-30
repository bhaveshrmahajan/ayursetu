package com.userservice.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name = "user")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank(message = "Name is required")
        @Size(min = 4 , max = 20, message = "Name must be minimum 4 character and maximum 20 character")
        private String name;
        @NotBlank(message = "Email is required")
        private String email;
        @NotBlank(message = "Password is required")
        @Size(min = 8 , message = "Password must be minimum 8 character")
        private String password;
        @Enumerated(EnumType.STRING)
        @NotNull(message = "Role is required")
        private Role role;
    }


