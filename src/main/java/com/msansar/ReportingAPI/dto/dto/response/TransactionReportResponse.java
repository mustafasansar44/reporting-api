package com.msansar.ReportingAPI.dto.dto.response;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.enums.Status;
import java.util.List;

public record TransactionReportResponse(
        Status status,
        List<TransactionReport> response) {
}


