package com.msansar.ReportingAPI.dto.transaction;

import java.util.List;

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


