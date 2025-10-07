package com.msansar.ReportingAPI.service;

import com.msansar.ReportingAPI.dto.dto.request.TransactionGetRequest;
import com.msansar.ReportingAPI.dto.dto.request.TransactionQueryRequest;
import com.msansar.ReportingAPI.dto.dto.request.TransactionReportRequest;
import com.msansar.ReportingAPI.dto.dto.response.TransactionGetResponse;
import com.msansar.ReportingAPI.dto.dto.response.TransactionQueryResponse;
import com.msansar.ReportingAPI.dto.dto.response.TransactionReportResponse;
import com.msansar.ReportingAPI.dto.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.enums.ErrorCode;
import com.msansar.ReportingAPI.enums.FilterField;
import com.msansar.ReportingAPI.enums.Operation;
import com.msansar.ReportingAPI.enums.PaymentMethod;
import com.msansar.ReportingAPI.enums.Status;
import com.msansar.ReportingAPI.model.Acquirer;
import com.msansar.ReportingAPI.model.Customer;
import com.msansar.ReportingAPI.model.Merchant;
import com.msansar.ReportingAPI.model.Transaction;
import com.msansar.ReportingAPI.repository.TransactionRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.ZoneId;

class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionService transactionService;

    // Global test constants
    private static final String BASE_URL = "http://localhost/api/transactions";
    private Date now;
    private Date yesterday;
    private Date fiftyYearsAgo;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
        
        // Initialize dates for each test
        now = new Date();
        yesterday = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        fiftyYearsAgo = Date.from(LocalDate.now().minusYears(50).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void whenGetTransactionReportIsCalledWithValidRequest_thenItShouldReturnTransactionReportResponse() {
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
        TransactionReportRequest requestWithNullMerchant = new TransactionReportRequest(yesterday, now, null, 1L);
        
        List<TransactionReport> reports = List.of(new TransactionReport("USD", new BigDecimal("100"), 1L));
        when(transactionRepository.getTransactionReport(yesterday, now, null, 1L)).thenReturn(reports);

        transactionService.getTransactionReport(requestWithNullMerchant);

        verify(transactionRepository, times(1)).getTransactionReport(yesterday, now, null, 1L);
    }

    @Test
    public void whenFromDateIsNull_thenItShouldThrowIllegalArgumentException() {
        TransactionReportRequest requestWithNullFromDate = new TransactionReportRequest(null, now, 1L, 1L);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.getTransactionReport(requestWithNullFromDate)
        );

        Assertions.assertEquals("fromDate and toDate are mandatory.", exception.getMessage());
    }

    @Test
    public void whenToDateIsNull_thenItShouldThrowIllegalArgumentException() {
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

    // ==================== listTransactions Tests ====================

    @Test
    public void whenListTransactionsWithAllParameters_thenItShouldPassAllToRepository() {
        // Tüm parametrelerin geçerli olduğu test
        TransactionQueryRequest request = new TransactionQueryRequest(
                yesterday, now, Status.APPROVED, Operation.DIRECT, 1L, 2L,
                PaymentMethod.CREDITCARD, ErrorCode.DO_NOT_HONOR,
                FilterField.REFERENCE_NO, "TRX2024101234567", 1, 20);

        List<Transaction> transactions = createMockTransactions(10);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                eq(yesterday), eq(now), eq(1L), eq(2L), eq(Operation.DIRECT),
                eq(PaymentMethod.CREDITCARD), eq(ErrorCode.DO_NOT_HONOR),
                isNull(), isNull(), eq("TRX2024101234567"), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        TransactionQueryResponse response = transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository, times(1)).findTransactions(
                eq(yesterday), eq(now), eq(1L), eq(2L), eq(Operation.DIRECT),
                eq(PaymentMethod.CREDITCARD), eq(ErrorCode.DO_NOT_HONOR),
                isNull(), isNull(), eq("TRX2024101234567"), isNull(), ArgumentMatchers.any(Pageable.class));

        Assertions.assertEquals(20, response.per_page());
        Assertions.assertEquals(1, response.current_page());
        Assertions.assertEquals(10, response.data().size());
    }

    @Test
    public void whenListTransactionsWithEmptyRequest_thenItShouldPassNullsToRepository() {
        // Hiçbir parametrenin olmadığı test (boş request {})
        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(50);
        Page<Transaction> page = new PageImpl<>(transactions, PageRequest.of(0, 50), 50);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        TransactionQueryResponse response = transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository, times(1)).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));

        Assertions.assertEquals(50, response.per_page()); // default değer
        Assertions.assertEquals(1, response.current_page()); // default değer
        Assertions.assertEquals(50, response.data().size());
    }

    @Test
    public void whenListTransactionsWithOnlyFromDate_thenItShouldFilterByFromDate() {
        TransactionQueryRequest request = new TransactionQueryRequest(
                yesterday, null, null, null, null, null, null, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                eq(yesterday), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                eq(yesterday), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithOnlyToDate_thenItShouldFilterByToDate() {
        TransactionQueryRequest request = new TransactionQueryRequest(
                null, now, null, null, null, null, null, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), eq(now), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), eq(now), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithOnlyMerchantId_thenItShouldFilterByMerchantId() {
        Long merchantId = 1L;

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, merchantId, null, null, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), eq(merchantId), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), eq(merchantId), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithOnlyAcquirerId_thenItShouldFilterByAcquirerId() {
        Long acquirerId = 2L;

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, acquirerId, null, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), eq(acquirerId), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), eq(acquirerId), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithOnlyOperation_thenItShouldFilterByOperation() {
        Operation operation = Operation.DIRECT;

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, operation, null, null, null, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), eq(operation), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), eq(operation), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithOnlyPaymentMethod_thenItShouldFilterByPaymentMethod() {
        PaymentMethod paymentMethod = PaymentMethod.CREDITCARD;

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, paymentMethod, null, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), eq(paymentMethod), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), eq(paymentMethod), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithOnlyErrorCode_thenItShouldFilterByErrorCode() {
        ErrorCode errorCode = ErrorCode.DO_NOT_HONOR;

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, errorCode, null, null, null, null);

        List<Transaction> transactions = createMockTransactions(5);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), eq(errorCode),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), eq(errorCode),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithTransactionUuidFilter_thenItShouldFilterByTransactionUuid() {
        String uuid = "a7b3c4d5-e6f7-8901-2345-6789abcdef01";

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null,
                FilterField.TRANSACTION_UUID, uuid, null, null);

        List<Transaction> transactions = createMockTransactions(1);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                eq(uuid), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                eq(uuid), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithCustomerEmailFilter_thenItShouldFilterByCustomerEmail() {
        String email = "mustafasansar44@gmail.com";

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null,
                FilterField.CUSTOMER_EMAIL, email, null, null);

        List<Transaction> transactions = createMockTransactions(3);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), eq(email), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), eq(email), isNull(), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithReferenceNoFilter_thenItShouldFilterByReferenceNo() {
        String referenceNo = "TRX2024101234567";

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null,
                FilterField.REFERENCE_NO, referenceNo, null, null);

        List<Transaction> transactions = createMockTransactions(2);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), eq(referenceNo), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), eq(referenceNo), isNull(), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithCardPanFilter_thenItShouldFilterByCardPan() {
        String cardPan = "5406675406675403";

        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null,
                FilterField.CARD_PAN, cardPan, null, null);

        List<Transaction> transactions = createMockTransactions(1);
        Page<Transaction> page = new PageImpl<>(transactions);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), eq(cardPan), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        transactionService.listTransactions(request, BASE_URL);

        verify(transactionRepository).findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), eq(cardPan), ArgumentMatchers.any(Pageable.class));
    }

    @Test
    public void whenListTransactionsWithPage_thenItShouldUseCorrectPageable() {
        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null, null, null, 2, null);

        List<Transaction> transactions = createMockTransactions(10);
        Page<Transaction> page = new PageImpl<>(transactions, PageRequest.of(1, 50), 100);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        TransactionQueryResponse response = transactionService.listTransactions(request, BASE_URL);

        Assertions.assertEquals(2, response.current_page());
        Assertions.assertEquals(50, response.per_page()); // default
    }

    @Test
    public void whenListTransactionsWithPerPage_thenItShouldUseCorrectPageable() {
        TransactionQueryRequest request = new TransactionQueryRequest(
                null, null, null, null, null, null, null, null, null, null, null, 25);

        List<Transaction> transactions = createMockTransactions(25);
        Page<Transaction> page = new PageImpl<>(transactions, PageRequest.of(0, 25), 25);

        when(transactionRepository.findTransactions(
                isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(),
                isNull(), isNull(), isNull(), isNull(), ArgumentMatchers.any(Pageable.class)))
                .thenReturn(page);

        TransactionQueryResponse response = transactionService.listTransactions(request, BASE_URL);

        Assertions.assertEquals(1, response.current_page()); // default
        Assertions.assertEquals(25, response.per_page());
    }

    // ==================== getTransaction Tests ====================

    @Test
    public void whenGetTransactionWithValidId_thenItShouldReturnTransactionGetResponse() {
        String transactionId = "a7b3c4d5-e6f7-8901-2345-6789abcdef01";
        TransactionGetRequest request = new TransactionGetRequest(transactionId);

        List<Transaction> transactions = createMockTransactions(1);
        Transaction transaction = transactions.get(0);
        transaction.setTransactionId(transactionId);
        
        when(transactionRepository.findByTransactionId(transactionId))
                .thenReturn(Optional.of(transaction));

        TransactionGetResponse response = transactionService.getTransaction(request);

        verify(transactionRepository, times(1)).findByTransactionId(transactionId);
        Assertions.assertNotNull(response);
    }

    @Test
    public void whenGetTransactionWithInvalidId_thenItShouldThrowIllegalArgumentException() {
        TransactionGetRequest request = new TransactionGetRequest(null);

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.getTransaction(request)
        );

        Assertions.assertEquals("transactionId is mandatory.", exception.getMessage());
    }

    // Helper method to create mock transactions
    private List<Transaction> createMockTransactions(int count) {
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Transaction transaction = new Transaction();
            transaction.setId((long) (i + 1));
            transaction.setAmount(new BigDecimal("100.00"));
            transaction.setCurrency("USD");
            transaction.setOperation(Operation.DIRECT);
            transaction.setPaymentMethod(PaymentMethod.CREDITCARD);
            transaction.setCreatedAt(new Date());
            transaction.setRefundable(true);
            transaction.setReceived(true);
            transaction.setErrorCode(null);

            Merchant merchant = new Merchant();
            merchant.setId((long) (i + 1));
            merchant.setName("merchant" + i);
            merchant.setReferenceNo("REF" + i);
            transaction.setMerchant(merchant);

            Acquirer acquirer = new Acquirer();
            acquirer.setId((long) (i + 1));
            acquirer.setName("acquirer" + i);
            transaction.setAcquirer(acquirer);

            Customer customer = new Customer();
            customer.setId((long) (i + 1));
            customer.setEmail("customer" + i + "@test.com");
            customer.setBillingFirstName("customer" + i);
            customer.setBillingLastName("lastname" + i);
            transaction.setCustomer(customer);

            transactions.add(transaction);
        }

        return transactions;
    }
}
