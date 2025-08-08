package com.userservice.services;

import com.userservice.dto.LoginDto;
import com.userservice.dto.UserDto;
import com.userservice.entities.PasswordResetToken;
import com.userservice.entities.Role;
import com.userservice.entities.User;
import com.userservice.exception.InvalidCredentialsException;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.exception.UserAlreadyExistsException;
import com.userservice.repository.PasswordResetTokenRepository;
import com.userservice.repository.UserRepository;
import com.userservice.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserServiceImple implements UserService{

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public UserDto registerUser(UserDto dto) {
        // Check if user exists first (fast operation)
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        User savedUser = userRepo.save(user);
        
        // Send welcome email asynchronously to avoid blocking the response
        CompletableFuture.runAsync(() -> {
            try {
                emailService.sendWelcomeEmail(savedUser.getEmail(), savedUser.getName());
            } catch (Exception e) {
                System.err.println("Failed to send welcome email: " + e.getMessage());
            }
        });
        
        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public String login(LoginDto req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + req.getEmail()));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + email));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        User existingUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Update fields if provided
        if (userDto.getName() != null && !userDto.getName().trim().isEmpty()) {
            existingUser.setName(userDto.getName());
        }
        
        if (userDto.getPassword() != null && !userDto.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        
        if (userDto.getRole() != null) {
            existingUser.setRole(userDto.getRole());
        }

        User updatedUser = userRepo.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        userRepo.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        
        // Save reset token to database
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(resetToken);
        passwordResetToken.setEmail(email);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // Token expires in 24 hours
        passwordResetToken.setUsed(false);
        
        passwordResetTokenRepository.save(passwordResetToken);
        
        // Send email with reset token asynchronously
        CompletableFuture.runAsync(() -> {
            try {
                emailService.sendPasswordResetEmail(email, resetToken);
                System.out.println("Password reset token generated and email sent to: " + email);
            } catch (Exception e) {
                System.err.println("Failed to send password reset email: " + e.getMessage());
            }
        });
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        // Find the most recent unused reset token for this email
        PasswordResetToken resetToken = passwordResetTokenRepository.findByEmailAndUsedFalse(email)
                .orElseThrow(() -> new ResourceNotFoundException("No valid reset token found for email: " + email));
        
        // Check if token is expired
        if (resetToken.isExpired()) {
            throw new RuntimeException("Reset token has expired");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        
        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
        
        System.out.println("Password reset successfully for email: " + email);
    }

    @Override
    public void resetPasswordWithToken(String token, String newPassword) {
        // Find the reset token
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid reset token"));
        
        // Check if token is expired
        if (resetToken.isExpired()) {
            throw new RuntimeException("Reset token has expired");
        }
        
        // Check if token is already used
        if (resetToken.isUsed()) {
            throw new RuntimeException("Reset token has already been used");
        }
        
        // Find user by email
        User user = userRepo.findByEmail(resetToken.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + resetToken.getEmail()));
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        
        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
        
        System.out.println("Password reset successfully using token for email: " + resetToken.getEmail());
    }
}

