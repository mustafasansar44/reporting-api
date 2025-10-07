package com.msansar.ReportingAPI.dto.dto.response;

import com.msansar.ReportingAPI.dto.dto.transaction.AcquirerInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.CustomerInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.MerchantFx;
import com.msansar.ReportingAPI.dto.dto.transaction.MerchantInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionInfo;

public record TransactionGetResponse(
        MerchantFx merchantFx,
        CustomerInfo customerInfo,
        MerchantInfo merchant,
        Boolean received,
        TransactionInfo transaction,
        AcquirerInfo acquirer,
        Boolean refundable
) {}