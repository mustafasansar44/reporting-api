package com.msansar.ReportingAPI.dto.converter;

import com.msansar.ReportingAPI.dto.dto.transaction.CustomerInfo;
import com.msansar.ReportingAPI.model.Customer;

public class CustomerConverter {
    public static CustomerInfo convert(Customer customer) {
        return new CustomerInfo(customer.getNumber(), customer.getEmail(), customer.getBillingFirstName(), customer.getBillingLastName());
    }
}
