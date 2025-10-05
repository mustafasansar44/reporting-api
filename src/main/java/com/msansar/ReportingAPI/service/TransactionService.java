package com.msansar.ReportingAPI.service;

import java.util.List;
import java.util.stream.Collectors;

import com.msansar.ReportingAPI.dto.converter.TransactionConverter;
import com.msansar.ReportingAPI.dto.transaction.TransactionQueryItem;
import com.msansar.ReportingAPI.dto.transaction.TransactionQueryRequest;
import com.msansar.ReportingAPI.dto.transaction.TransactionQueryResponse;
import com.msansar.ReportingAPI.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportResponse;
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

        if (request.fromDate() == null || request.toDate() == null) {
            throw new IllegalArgumentException("fromDate and toDate are mandatory.");
        }

        List<TransactionReport> reports = transactionRepository.getTransactionReport(
                request.fromDate(),
                request.toDate(),
                request.merchantId(),
                request.acquirerId()
        );

        List<TransactionReport> responseList = reports.stream()
                .map(report -> {
                    return new TransactionReport(
                            report.currency(),
                            report.amount(),
                            report.count()
                    );
                })
                .collect(Collectors.toList());

        return new TransactionReportResponse(
                Status.APPROVED,
                responseList);
    }

    public TransactionQueryResponse listTransactions(TransactionQueryRequest request, String baseUrl) {
        int perPage = request.perPage() != null ? request.perPage() : 50;
        int pageIndex = (request.page() != null && request.page() > 0) ? request.page() - 1 : 0;
        Pageable pageable = PageRequest.of(pageIndex, perPage);

        Page<Transaction> page = transactionRepository.findTransactions(
                request.fromDate(),
                request.toDate(),
                request.merchantId(),
                request.acquirerId(),
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
}
