package com.msansar.ReportingAPI.model;

import java.math.BigDecimal;
import java.util.Date;

import com.msansar.ReportingAPI.enums.ErrorCode;
import com.msansar.ReportingAPI.enums.Operation;
import com.msansar.ReportingAPI.enums.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private Date createdAt;  // Date of the transactions
    private String currency;  // Currency of the transactions
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Operation operation;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Boolean received = false;
    private Boolean refundable = false;
    @Enumerated(EnumType.STRING)
    private ErrorCode errorCode;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "acquirer_id", nullable = false)
    private Acquirer acquirer;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
