package com.msansar.ReportingAPI.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.msansar.ReportingAPI.dto.dto.auth.LoginRequest;
import com.msansar.ReportingAPI.dto.dto.auth.LoginResponse;
import com.msansar.ReportingAPI.dto.dto.auth.RegisterRequest;
import com.msansar.ReportingAPI.dto.dto.auth.RegisterResponse;
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
                .orElseThrow(() -> new RuntimeException("User not found")); // TODO: UserNotFoundException ile değiştirilecek
        
        // Şifre kontrolü
        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password"); // TODO: Custom exception ile değiştirilecek
        }
        
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, Status.APPROVED));
    }

    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest) {
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        Merchant user = new Merchant(registerRequest.email(), hashedPassword);
        authRepository.save(user);
        RegisterResponse registerResponse = new RegisterResponse(registerRequest.email(), Status.APPROVED);
        return ResponseEntity.ok(registerResponse); // TODO: RegisterResponse'u düzeltilebilir.
    }

}
