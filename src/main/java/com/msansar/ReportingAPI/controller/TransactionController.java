package com.msansar.ReportingAPI.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msansar.ReportingAPI.dto.dto.request.TransactionGetRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionGetResponse;
import com.msansar.ReportingAPI.dto.dto.request.TransactionQueryRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionQueryResponse;
import com.msansar.ReportingAPI.dto.dto.request.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionReportResponse;
import com.msansar.ReportingAPI.dto.dto.request.ClientRequest;
import com.msansar.ReportingAPI.dto.dto.response.ClientResponse;
import com.msansar.ReportingAPI.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v3")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionGetResponse> getTransaction(@Valid @RequestBody TransactionGetRequest requestBody) {
        TransactionGetResponse response = transactionService.getTransaction(requestBody);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transactions/report")
    public ResponseEntity<TransactionReportResponse> getTransactionReport(@Valid @RequestBody TransactionReportRequest requestBody) {
        TransactionReportResponse report = transactionService.getTransactionReport(requestBody);
        return ResponseEntity.ok(report);
    }

    @PostMapping("/transactions/list")
    public ResponseEntity<TransactionQueryResponse> list(@Valid @RequestBody TransactionQueryRequest requestBody) {
        TransactionQueryResponse resp = transactionService.listTransactions(requestBody, "/api/v3/transactions/list");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/client")
    public ResponseEntity<ClientResponse> getClient(@Valid @RequestBody ClientRequest requestBody) {
        ClientResponse response = transactionService.getClient(requestBody);
        return ResponseEntity.ok(response);
    }

}