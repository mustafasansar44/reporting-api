package com.msansar.ReportingAPI.dto.dto.transaction;

public record FilterParams(
    String transactionUuid,
    String customerEmail,
    String referenceNo,
    String cardPan
) {}
