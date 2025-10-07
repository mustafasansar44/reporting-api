package com.msansar.ReportingAPI.service;

import java.util.List;
import java.util.stream.Collectors;

import com.msansar.ReportingAPI.dto.converter.TransactionConverter;
import com.msansar.ReportingAPI.dto.dto.transaction.FilterParams;
import com.msansar.ReportingAPI.dto.dto.request.TransactionGetRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionGetResponse;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionQueryItem;
import com.msansar.ReportingAPI.dto.dto.request.TransactionQueryRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionQueryResponse;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.dto.dto.request.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionReportResponse;
import com.msansar.ReportingAPI.dto.dto.request.ClientRequest;
import com.msansar.ReportingAPI.dto.dto.response.ClientResponse;
import com.msansar.ReportingAPI.enums.FilterField;
import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.msansar.ReportingAPI.model.Transaction;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionReportResponse getTransactionReport(TransactionReportRequest request) {
        List<TransactionReport> reports = transactionRepository.getTransactionReport(
                request.fromDate(),
                request.toDate(),
                request.merchantId(),
                request.acquirerId()
        );

        return new TransactionReportResponse(Status.APPROVED, reports);
    }

    public TransactionQueryResponse listTransactions(TransactionQueryRequest request, String baseUrl) {
        int perPage = request.perPage() != null ? request.perPage() : 50;
        int pageIndex = (request.page() != null && request.page() > 0) ? request.page() - 1 : 0;
        Pageable pageable = PageRequest.of(pageIndex, perPage);

        FilterParams filterParams = extractFilterParams(request.filterField(), request.filterValue());

        Page<Transaction> page = transactionRepository.findTransactions(
                request.fromDate(),
                request.toDate(),
                request.merchantId(),
                request.acquirerId(),
                request.operation(),
                request.paymentMethod(),
                request.errorCode(),
                filterParams.transactionUuid(),
                filterParams.customerEmail(),
                filterParams.referenceNo(),
                filterParams.cardPan(),
                pageable);

        List<TransactionQueryItem> items = page.getContent().stream()
                .map(transaction -> TransactionConverter.convertTransactionQueryResponse(transaction))
                .collect(Collectors.toList());

        Integer currentPage = pageIndex + 1;
        String nextUrl = page.hasNext() ? baseUrl + "?page=" + (currentPage + 1) : null;
        String prevUrl = page.hasPrevious() ? baseUrl + "?page=" + (currentPage - 1) : null;
        Integer from = page.getNumberOfElements() == 0 ? 0 : (pageIndex * perPage) + 1;
        Integer to = pageIndex * perPage + page.getNumberOfElements();

        return new TransactionQueryResponse(perPage, currentPage, nextUrl, prevUrl, from, to, items);
    }

    public TransactionGetResponse getTransaction(TransactionGetRequest request) {
        Transaction transaction = transactionRepository.findByTransactionId(request.transactionId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + request.transactionId()));

        return TransactionConverter.convertToGetResponse(transaction);
    }

    public ClientResponse getClient(ClientRequest request) {
        Transaction transaction = transactionRepository.findByTransactionId(request.transactionId())
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + request.transactionId()));

        return TransactionConverter.convertToClientResponse(transaction);
    }

    /**
     * FilterField ve filterValue parametrelerine göre filtreleme parametrelerini hazırlar
     */
    private FilterParams extractFilterParams(FilterField filterField, String filterValue) {
        if (filterField == null || filterValue == null) {
            return new FilterParams(null, null, null, null);
        }

        return switch (filterField) {
            case TRANSACTION_UUID -> new FilterParams(filterValue, null, null, null);
            case CUSTOMER_EMAIL -> new FilterParams(null, filterValue, null, null);
            case REFERENCE_NO -> new FilterParams(null, null, filterValue, null);
            case CARD_PAN -> new FilterParams(null, null, null, filterValue);
            case CUSTOM_DATA -> new FilterParams(null, null, null, null);
        };
    }

}
