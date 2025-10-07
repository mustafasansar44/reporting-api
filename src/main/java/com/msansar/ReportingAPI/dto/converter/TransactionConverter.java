package com.msansar.ReportingAPI.dto.converter;

import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.model.Transaction;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.MerchantFx;
import com.msansar.ReportingAPI.dto.dto.transaction.CustomerInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.MerchantInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.AcquirerInfo;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionQueryItem;
import com.msansar.ReportingAPI.dto.dto.response.TransactionGetResponse;
import com.msansar.ReportingAPI.dto.dto.response.ClientResponse;

public class TransactionConverter {
    public static TransactionInfo convert(Transaction transaction) {
        return new TransactionInfo(
                transaction.getMerchant().getReferenceNo(),
                Status.APPROVED,
                transaction.getOperation(),
                transaction.getOperation().name() + " is APPROVED",
                transaction.getCreatedAt(),
                transaction.getTransactionId());
    }

    // TransactionQueryResponse
    public static TransactionQueryItem convertTransactionQueryResponse(Transaction transaction) {
        MerchantFx merchantFx = new MerchantFx(transaction.getAmount(), transaction.getCurrency());
        CustomerInfo customerInfo = CustomerConverter.convert(transaction.getCustomer());
        MerchantInfo merchantInfo = MerchantConverter.convert(transaction.getMerchant());
        TransactionInfo transactionInfo = TransactionConverter.convert(transaction);
        AcquirerInfo acquirerInfo = AcquirerConverter.convert(transaction.getAcquirer(), transaction.getPaymentMethod().name());

        return new TransactionQueryItem(
                merchantFx,
                customerInfo,
                merchantInfo,
                transaction.getReceived(),
                transactionInfo,
                acquirerInfo,
                transaction.getRefundable(),
                transaction.getErrorCode());
    }

    public static TransactionGetResponse convertToGetResponse(Transaction transaction) {
        MerchantFx merchantFx = new MerchantFx(transaction.getAmount(), transaction.getCurrency());
        CustomerInfo customerInfo = CustomerConverter.convert(transaction.getCustomer());
        MerchantInfo merchantInfo = MerchantConverter.convert(transaction.getMerchant());
        TransactionInfo transactionInfo = TransactionConverter.convert(transaction);
        AcquirerInfo acquirerInfo = AcquirerConverter.convert(transaction.getAcquirer(), transaction.getPaymentMethod().name());

        return new TransactionGetResponse(
                merchantFx,
                customerInfo,
                merchantInfo,
                transaction.getReceived(),
                transactionInfo,
                acquirerInfo,
                transaction.getRefundable());
    }

    public static ClientResponse convertToClientResponse(Transaction transaction) {
        CustomerInfo customerInfo = CustomerConverter.convert(transaction.getCustomer());
        return new ClientResponse(customerInfo);
    }

}
