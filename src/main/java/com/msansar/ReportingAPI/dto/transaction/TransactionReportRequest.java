package com.msansar.ReportingAPI.dto.transaction;

import java.util.Date;

public record TransactionReportRequest(
        Date fromDate,
        Date toDate,
        Long merchantId,
        Long acquirerId
) {
}