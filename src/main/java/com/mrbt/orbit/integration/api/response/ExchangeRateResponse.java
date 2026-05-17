package com.mrbt.orbit.integration.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ExchangeRateResponse(UUID id, String baseCurrency, String targetCurrency, BigDecimal rate, String source,
		OffsetDateTime fetchedAt) {
}
