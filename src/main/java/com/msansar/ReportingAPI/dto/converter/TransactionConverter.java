package com.msansar.ReportingAPI.dto.converter;

import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.model.Transaction;
import com.msansar.ReportingAPI.dto.transaction.TransactionInfo;
import com.msansar.ReportingAPI.dto.transaction.MerchantFx;
import com.msansar.ReportingAPI.dto.transaction.CustomerInfo;
import com.msansar.ReportingAPI.dto.transaction.MerchantInfo;
import com.msansar.ReportingAPI.dto.transaction.Ipn;
import com.msansar.ReportingAPI.dto.transaction.AcquirerInfo;
import com.msansar.ReportingAPI.dto.transaction.TransactionQueryItem;

public class TransactionConverter {
    public static TransactionInfo convert(Transaction transaction) {
        return new TransactionInfo(
                transaction.getMerchant().getReferenceNo(),
                Status.APPROVED,
                transaction.getOperation(),
                transaction.getOperation().name() + " is APPROVED",
                transaction.getCreatedAt(),
                String.valueOf(transaction.getId()));
    }

    // TransactionQueryResponse
    public static TransactionQueryItem convertTransactionQueryResponse(Transaction transaction) {
        MerchantFx merchantFx = new MerchantFx(transaction.getAmount(), transaction.getCurrency());
        CustomerInfo customerInfo = CustomerConverter.convert(transaction.getCustomer());
        MerchantInfo merchantInfo = MerchantConverter.convert(transaction.getMerchant());
        Ipn ipn = new Ipn(transaction.getReceived());
        TransactionInfo transactionInfo = TransactionConverter.convert(transaction);
        AcquirerInfo acquirerInfo = AcquirerConverter.convert(transaction.getAcquirer(), transaction.getPaymentMethod().name());

        return new TransactionQueryItem(
                merchantFx,
                customerInfo,
                merchantInfo,
                ipn,
                transactionInfo,
                acquirerInfo,
                transaction.getRefundable(),
                transaction.getErrorCode());
    }

}
