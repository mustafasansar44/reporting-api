package com.msansar.ReportingAPI.dto.dto.transaction;

import com.msansar.ReportingAPI.enums.ErrorCode;

public record TransactionQueryItem(
        MerchantFx merchantFx,
        CustomerInfo customerInfo,
        MerchantInfo merchant,
        Boolean received,
        TransactionInfo transaction,
        AcquirerInfo acquirer,
        Boolean refundable,
        ErrorCode errorCode
) {}


