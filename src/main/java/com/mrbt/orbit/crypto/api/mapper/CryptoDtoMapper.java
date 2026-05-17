package com.mrbt.orbit.crypto.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.crypto.api.response.CryptoAssetResponse;
import com.mrbt.orbit.crypto.api.response.CryptoPortfolioSnapshotResponse;
import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;

@Component
public class CryptoDtoMapper {

	public CryptoAssetResponse toResponse(CryptoAsset domain) {
		if (domain == null) {
			return null;
		}
		return new CryptoAssetResponse(domain.getId(), domain.getSymbol(), domain.getName(), domain.getCoingeckoId(),
				domain.getCurrentPriceUsd(), domain.getMarketCapRank(), domain.getUpdatedAt());
	}

	public CryptoPortfolioSnapshotResponse toResponse(CryptoPortfolioSnapshot domain) {
		if (domain == null) {
			return null;
		}
		return new CryptoPortfolioSnapshotResponse(domain.getId(), domain.getUserId(), domain.getSnapshotDate(),
				domain.getTotalValueUsd(), domain.getHoldingsJson(), domain.getCreatedAt());
	}

}
