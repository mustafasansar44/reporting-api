package com.msansar.ReportingAPI.dto.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClientRequest(
        @NotBlank(message = "transactionId is mandatory")
        String transactionId
) {}

