package com.msansar.ReportingAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msansar.ReportingAPI.dto.transaction.TransactionQueryRequest;
import com.msansar.ReportingAPI.dto.transaction.TransactionQueryResponse;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportResponse;
import com.msansar.ReportingAPI.service.TransactionService;

@RestController
@RequestMapping("/api/v3/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/report")
    public ResponseEntity<TransactionReportResponse> getTransactionReport(@RequestBody TransactionReportRequest requestBody) {
        TransactionReportResponse report = transactionService.getTransactionReport(requestBody);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/list")
    public ResponseEntity<TransactionQueryResponse> list(@RequestBody TransactionQueryRequest requestBody) {
        TransactionQueryResponse resp = transactionService.listTransactions(requestBody, "/api/v3/transactions/list");
        return ResponseEntity.ok(resp);
    }

}