package com.userservice.services;

import com.userservice.dto.LoginDto;
import com.userservice.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto dto);
    String login(LoginDto dto);
    UserDto getUserByEmail(String email);
    UserDto updateUser(String email, UserDto userDto);
    void deleteUser(String email);
    List<UserDto> getAllUsers();
    void forgotPassword(String email);
    void resetPassword(String email, String newPassword);
    void resetPasswordWithToken(String token, String newPassword);
}
