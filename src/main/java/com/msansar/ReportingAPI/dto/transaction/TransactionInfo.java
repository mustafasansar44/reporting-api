package com.msansar.ReportingAPI.dto.transaction;

import java.util.Date;

import com.msansar.ReportingAPI.enums.Operation;
import com.msansar.ReportingAPI.enums.Status;

public record TransactionInfo(
        String referenceNo,
        Status status,
        Operation operation,
        String message,
        Date created_at,
        String transactionId) {}

