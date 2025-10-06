package com.msansar.ReportingAPI.dto.transaction;

import com.msansar.ReportingAPI.enums.ErrorCode;

public record TransactionQueryItem(
        MerchantFx merchantFx,
        CustomerInfo customerInfo,
        MerchantInfo merchant,
        Ipn ipn,
        TransactionInfo transaction,
        AcquirerInfo acquirer,
        Boolean refundable,
        ErrorCode errorCode
) {}


