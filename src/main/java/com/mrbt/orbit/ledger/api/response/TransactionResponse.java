package com.mrbt.orbit.ledger.api.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record TransactionResponse(UUID id, UUID accountId, UUID categoryId, BigDecimal amount, String currencyCode,
		BigDecimal exchangeRate, String description, Instant transactionDate, String status, Boolean isReviewed,
		OffsetDateTime createdAt) {
}
