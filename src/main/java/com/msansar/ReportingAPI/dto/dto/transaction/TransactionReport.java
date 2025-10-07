package com.msansar.ReportingAPI.dto.dto.transaction;

import java.math.BigDecimal;

public record TransactionReport(
        String currency,
        BigDecimal amount,
        long count) {
}
