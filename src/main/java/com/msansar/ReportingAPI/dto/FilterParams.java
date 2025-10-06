package com.msansar.ReportingAPI.dto;

public record FilterParams(
    String transactionUuid,
    String customerEmail,
    String referenceNo,
    String cardPan
) {}
