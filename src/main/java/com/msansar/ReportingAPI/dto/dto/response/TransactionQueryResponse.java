package com.msansar.ReportingAPI.dto.dto.response;

import java.util.List;

import com.msansar.ReportingAPI.dto.dto.transaction.TransactionQueryItem;

/**
 * Paginated response for Transaction Query.
 */
public record TransactionQueryResponse(
        Integer per_page,
        Integer current_page,
        String next_page_url,
        String prev_page_url,
        Integer from,
        Integer to,
        List<TransactionQueryItem> data
) {}


