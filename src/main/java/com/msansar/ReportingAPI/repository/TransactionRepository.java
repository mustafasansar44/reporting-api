package com.msansar.ReportingAPI.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.msansar.ReportingAPI.dto.transaction.TransactionReport;
import com.msansar.ReportingAPI.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT new com.msansar.ReportingAPI.dto.transaction.TransactionReport(t.currency, SUM(t.amount), COUNT(t)) " +
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
}
