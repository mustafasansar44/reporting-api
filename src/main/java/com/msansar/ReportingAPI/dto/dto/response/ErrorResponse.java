package com.msansar.ReportingAPI.dto.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(
    @JsonProperty("code")
    int code,
    
    @JsonProperty("status")
    String status,
    
    @JsonProperty("message")
    String message
) {}

