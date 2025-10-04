package com.msansar.ReportingAPI.dto.auth;

import com.msansar.ReportingAPI.enums.Status;

public record LoginResponse(String token, Status status) {}
