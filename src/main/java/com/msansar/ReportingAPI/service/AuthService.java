package com.msansar.ReportingAPI.service;

import org.springframework.stereotype.Service;

import com.msansar.ReportingAPI.dto.auth.LoginRequest;
import com.msansar.ReportingAPI.dto.auth.LoginResponse;
import com.msansar.ReportingAPI.dto.auth.RegisterRequest;
import com.msansar.ReportingAPI.dto.auth.RegisterResponse;
import com.msansar.ReportingAPI.model.User;
import com.msansar.ReportingAPI.repository.AuthRepository;
import com.msansar.ReportingAPI.enums.Status;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    private User getUserByEmail(String email) {
        return authRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found")); // TODO: UserNotFoundException ile değiştirilecek
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = getUserByEmail(loginRequest.email());
        return new LoginResponse(user.getId().toString(), Status.APPROVED); // TODO: user.getId() yerine token
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = new User(registerRequest.email(), registerRequest.password());  // TODO: PasswordEncoder ile şifreleri hashle
        authRepository.save(user);
        return new RegisterResponse(registerRequest.email(), registerRequest.password(), Status.APPROVED);
    }

}
