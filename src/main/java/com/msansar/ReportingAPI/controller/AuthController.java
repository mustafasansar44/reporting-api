package com.msansar.ReportingAPI.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msansar.ReportingAPI.dto.auth.LoginRequest;
import com.msansar.ReportingAPI.dto.auth.LoginResponse;
import com.msansar.ReportingAPI.dto.auth.RegisterRequest;
import com.msansar.ReportingAPI.dto.auth.RegisterResponse;
import com.msansar.ReportingAPI.service.AuthService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/v3/merchant/user") // Bu, tüm endpointlerin base path'i olacak
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
}
