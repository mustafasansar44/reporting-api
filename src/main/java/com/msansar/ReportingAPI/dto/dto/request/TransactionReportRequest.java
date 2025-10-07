package com.msansar.ReportingAPI.dto.dto.request;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

public record TransactionReportRequest(
        @NotNull(message = "fromDate is mandatory")
        Date fromDate,
        
        @NotNull(message = "toDate is mandatory")
        Date toDate,
        
        Long merchantId,
        Long acquirerId
) {
}