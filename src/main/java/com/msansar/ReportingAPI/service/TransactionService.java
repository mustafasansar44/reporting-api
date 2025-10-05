package com.msansar.ReportingAPI.service;

import java.util.List;
import java.util.stream.Collectors;

import com.msansar.ReportingAPI.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportResponse;
import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.repository.TransactionRepository;
import org.springframework.stereotype.Service;

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
}
