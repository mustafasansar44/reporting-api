package com.msansar.ReportingAPI.dto.dto.request;

import java.util.Date;

public record TransactionReportRequest(
        Date fromDate,
        Date toDate,
        Long merchantId,
        Long acquirerId
) {
}