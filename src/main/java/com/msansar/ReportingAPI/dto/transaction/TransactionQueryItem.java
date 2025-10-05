package com.msansar.ReportingAPI.dto.transaction;

public record TransactionQueryItem(
        MerchantFx merchantFx,
        CustomerInfo customerInfo,
        MerchantInfo merchant,
        Ipn ipn,
        TransactionInfo transaction,
        AcquirerInfo acquirer,
        Boolean refundable
) {}


