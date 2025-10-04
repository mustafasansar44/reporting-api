package com.msansar.ReportingAPI.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    String email, 
    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    String password
    ) {}