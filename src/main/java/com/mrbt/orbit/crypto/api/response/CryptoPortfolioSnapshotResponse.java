package com.mrbt.orbit.crypto.api.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CryptoPortfolioSnapshotResponse(UUID id, UUID userId, LocalDate snapshotDate, BigDecimal totalValueUsd,
		String holdingsJson, OffsetDateTime createdAt) {
}
