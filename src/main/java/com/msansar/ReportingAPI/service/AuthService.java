package com.msansar.ReportingAPI.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.msansar.ReportingAPI.dto.dto.auth.LoginRequest;
import com.msansar.ReportingAPI.dto.dto.auth.LoginResponse;
import com.msansar.ReportingAPI.dto.dto.auth.RegisterRequest;
import com.msansar.ReportingAPI.dto.dto.auth.RegisterResponse;
import com.msansar.ReportingAPI.exception.InvalidCredentialsException;
import com.msansar.ReportingAPI.exception.UserAlreadyExistsException;
import com.msansar.ReportingAPI.model.Merchant;
import com.msansar.ReportingAPI.repository.AuthRepository;
import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.util.JwtUtil;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Merchant user = authRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() -> new InvalidCredentialsException("Error: Merchant User credentials is not valid"));
        
        // Şifre kontrolü
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Error: Merchant User credentials is not valid");
        }
        
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, Status.APPROVED));
    }

    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest) {
        // Email zaten varsa hata fırlat
        if (authRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new UserAlreadyExistsException("Error: Merchant User with email " + registerRequest.email() + " already exists");
        }
        
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        Merchant user = new Merchant(registerRequest.email(), hashedPassword);
        authRepository.save(user);
        RegisterResponse registerResponse = new RegisterResponse(registerRequest.email(), Status.APPROVED);
        return ResponseEntity.ok(registerResponse);
    }

}
