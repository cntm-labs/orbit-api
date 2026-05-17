package com.mrbt.orbit.crypto.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.crypto.api.response.CryptoAssetResponse;
import com.mrbt.orbit.crypto.api.response.CryptoPortfolioSnapshotResponse;
import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;

class CryptoDtoMapperTest {

	private final CryptoDtoMapper mapper = new CryptoDtoMapper();

	@Test
	void toResponse_CryptoAsset_ShouldMapCorrectly() {
		CryptoAsset domain = CryptoAsset.builder().id(UUID.randomUUID()).symbol("BTC").name("Bitcoin")
				.coingeckoId("bitcoin").currentPriceUsd(new BigDecimal("60000.00")).marketCapRank(1)
				.updatedAt(OffsetDateTime.now()).build();

		CryptoAssetResponse response = mapper.toResponse(domain);

		assertThat(response).isNotNull();
		assertThat(response.symbol()).isEqualTo(domain.getSymbol());
		assertThat(response.currentPriceUsd()).isEqualByComparingTo(domain.getCurrentPriceUsd());
	}

	@Test
	void toResponse_Snapshot_ShouldMapCorrectly() {
		CryptoPortfolioSnapshot domain = CryptoPortfolioSnapshot.builder().id(UUID.randomUUID())
				.userId(UUID.randomUUID()).snapshotDate(LocalDate.now()).totalValueUsd(new BigDecimal("1000.00"))
				.holdingsJson("[]").createdAt(OffsetDateTime.now()).build();

		CryptoPortfolioSnapshotResponse response = mapper.toResponse(domain);

		assertThat(response).isNotNull();
		assertThat(response.totalValueUsd()).isEqualByComparingTo(domain.getTotalValueUsd());
	}
}
