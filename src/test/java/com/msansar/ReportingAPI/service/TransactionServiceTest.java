package com.msansar.ReportingAPI.service;

import com.msansar.ReportingAPI.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.transaction.TransactionReportResponse;
import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.repository.TransactionRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    public void whenGetTransactionReportIsCalledWithValidRequest_thenItShouldReturnTransactionReportResponse() {
        Date now = new Date();
        Date fiftyYearsAgo = Date.from(LocalDate.now().minusYears(50).atStartOfDay(ZoneId.systemDefault()).toInstant());

        TransactionReportRequest allTransactionsRequest = new TransactionReportRequest(fiftyYearsAgo, now, 1L, 1L);

        List<TransactionReport> reports = List.of(
                new TransactionReport("USD", new BigDecimal("100"), 1L),
                new TransactionReport("EUR", new BigDecimal("200"), 2L),
                new TransactionReport("TRY", new BigDecimal("300"), 3L));
        when(transactionRepository.getTransactionReport(fiftyYearsAgo, now, 1L, 1L)).thenReturn(reports);

        TransactionReportResponse expectedResponse = new TransactionReportResponse(Status.APPROVED, reports);
        TransactionReportResponse actualResponse = transactionService.getTransactionReport(allTransactionsRequest);
        
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void shouldCallRepositoryWithNullOptionals_whenMerchantOrAcquirerIdIsNull() {
        Date now = new Date();
        Date yesterday = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        TransactionReportRequest requestWithNullMerchant = new TransactionReportRequest(yesterday, now, null, 1L);
        
        List<TransactionReport> reports = List.of(new TransactionReport("USD", new BigDecimal("100"), 1L));
        when(transactionRepository.getTransactionReport(yesterday, now, null, 1L)).thenReturn(reports);

        transactionService.getTransactionReport(requestWithNullMerchant);

        verify(transactionRepository, times(1)).getTransactionReport(yesterday, now, null, 1L);
    }

    @Test
    public void whenFromDateIsNull_thenItShouldThrowIllegalArgumentException() {
        Date now = new Date();
        
        TransactionReportRequest requestWithNullFromDate = new TransactionReportRequest(null, now, 1L, 1L);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.getTransactionReport(requestWithNullFromDate)
        );

        Assertions.assertEquals("fromDate and toDate are mandatory.", exception.getMessage());
    }

    @Test
    public void whenToDateIsNull_thenItShouldThrowIllegalArgumentException() {
        Date yesterday = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        TransactionReportRequest requestWithNullToDate = new TransactionReportRequest(yesterday, null, 1L, 1L);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.getTransactionReport(requestWithNullToDate)
        );

        Assertions.assertEquals("fromDate and toDate are mandatory.", exception.getMessage());
    }

    @Test
    public void whenBothDatesAreNull_thenItShouldThrowIllegalArgumentException() {
        TransactionReportRequest requestWithBothDatesNull = new TransactionReportRequest(null, null, 1L, 1L);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.getTransactionReport(requestWithBothDatesNull)
        );

        Assertions.assertEquals("fromDate and toDate are mandatory.", exception.getMessage());
    }
}
