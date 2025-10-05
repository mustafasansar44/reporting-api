package com.msansar.ReportingAPI.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "customers")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number")
    private String number;

    @Column(nullable = false)
    private String email;

    @Column(name = "billing_first_name")
    private String billingFirstName;

    @Column(name = "billing_last_name")
    private String billingLastName;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Customer(String number, String email, String billingFirstName, String billingLastName) {
        this.number = number;
        this.email = email;
        this.billingFirstName = billingFirstName;
        this.billingLastName = billingLastName;
    }
}

