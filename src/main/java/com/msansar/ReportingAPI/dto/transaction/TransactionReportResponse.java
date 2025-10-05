package com.msansar.ReportingAPI.dto.transaction;
import com.msansar.ReportingAPI.enums.Status;
import java.util.List;

public record TransactionReportResponse(
        Status status,
        List<TransactionReport> response) {
}


