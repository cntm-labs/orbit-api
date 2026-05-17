package com.mrbt.orbit.crypto.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CryptoAssetResponse(UUID id, String symbol, String name, String coingeckoId, BigDecimal currentPriceUsd,
		Integer marketCapRank, OffsetDateTime updatedAt) {
}
