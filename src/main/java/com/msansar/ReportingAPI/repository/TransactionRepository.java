package com.msansar.ReportingAPI.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msansar.ReportingAPI.dto.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.enums.ErrorCode;
import com.msansar.ReportingAPI.enums.Operation;
import com.msansar.ReportingAPI.enums.PaymentMethod;
import com.msansar.ReportingAPI.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    Optional<Transaction> findByTransactionId(String transactionId);
    
    @Query("SELECT new com.msansar.ReportingAPI.dto.dto.transaction.TransactionReport(t.currency, SUM(t.amount), COUNT(t)) " +
            "FROM Transaction t " +
            "WHERE t.createdAt BETWEEN :fromDate AND :toDate " +
            "AND (:merchantId IS NULL OR t.merchant.id = :merchantId) " +
            "AND (:acquirerId IS NULL OR t.acquirer.id = :acquirerId) " +
            "GROUP BY t.currency")
    List<TransactionReport> getTransactionReport(
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("merchantId") Long merchantId,
            @Param("acquirerId") Long acquirerId);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.createdAt >= COALESCE(:fromDate, t.createdAt) " +
            "AND t.createdAt <= COALESCE(:toDate, t.createdAt) " +
            "AND t.merchant.id = COALESCE(:merchantId, t.merchant.id) " +
            "AND t.acquirer.id = COALESCE(:acquirerId, t.acquirer.id) " +
            "AND (:operation IS NULL OR t.operation = :operation) " +
            "AND (:paymentMethod IS NULL OR t.paymentMethod = :paymentMethod) " +
            "AND (:errorCode IS NULL OR t.errorCode = :errorCode) " +
            "AND (:transactionUuid IS NULL OR CAST(t.id AS string) = :transactionUuid) " +
            "AND (:customerEmail IS NULL OR t.customer.email = :customerEmail) " +
            "AND (:referenceNo IS NULL OR t.merchant.referenceNo = :referenceNo) " +
            "AND (:cardPan IS NULL OR t.customer.number = :cardPan)")
    Page<Transaction> findTransactions(
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate,
            @Param("merchantId") Long merchantId,
            @Param("acquirerId") Long acquirerId,
            @Param("operation") Operation operation,
            @Param("paymentMethod") PaymentMethod paymentMethod,
            @Param("errorCode") ErrorCode errorCode,
            @Param("transactionUuid") String transactionUuid,
            @Param("customerEmail") String customerEmail,
            @Param("referenceNo") String referenceNo,
            @Param("cardPan") String cardPan,
            Pageable pageable);
}
