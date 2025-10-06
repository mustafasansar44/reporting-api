package com.msansar.ReportingAPI.dto.transaction;

import java.util.Date;

import com.msansar.ReportingAPI.enums.ErrorCode;
import com.msansar.ReportingAPI.enums.FilterField;
import com.msansar.ReportingAPI.enums.Operation;
import com.msansar.ReportingAPI.enums.PaymentMethod;
import com.msansar.ReportingAPI.enums.Status;

/**
 * Request model for Transaction Query (/list) endpoint.
 */
public record TransactionQueryRequest(
        Date fromDate,
        Date toDate,
        Status status,
        Operation operation,
        Long merchantId,
        Long acquirerId,
        PaymentMethod paymentMethod,
        ErrorCode errorCode,
        FilterField filterField,
        String filterValue,
        Integer page,
        Integer perPage
) {
}


