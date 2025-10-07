package com.msansar.ReportingAPI.dto.dto.auth;

import com.msansar.ReportingAPI.enums.Status;

public record RegisterResponse(String email, Status status) {}
