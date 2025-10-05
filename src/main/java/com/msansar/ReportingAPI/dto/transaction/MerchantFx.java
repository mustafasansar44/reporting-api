package com.msansar.ReportingAPI.dto.transaction;

import java.math.BigDecimal;

public record MerchantFx(BigDecimal originalAmount, String originalCurrency) {}

