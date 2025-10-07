package com.msansar.ReportingAPI.dto.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    @Size(min = 0, max = 100, message = "Email must be between 10 and 100 characters")
    String email, 

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 0, max = 100, message = "Password must be between 6 and 100 characters")
    String password
) {}
