package com.mrbt.orbit.crypto.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.infrastructure.entity.CryptoAssetEntity;

@Component
public class CryptoAssetEntityMapper extends AbstractNullSafeMapper<CryptoAssetEntity, CryptoAsset> {

	@Override
	public CryptoAsset toDomain(CryptoAssetEntity entity) {
		if (entity == null) {
			return null;
		}
		return CryptoAsset.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.symbol(entity.getSymbol()).name(entity.getName()).coingeckoId(entity.getCoingeckoId())
				.currentPriceUsd(entity.getCurrentPriceUsd()).marketCapRank(entity.getMarketCapRank()).build();
	}

	@Override
	public CryptoAssetEntity toEntity(CryptoAsset domain) {
		if (domain == null) {
			return null;
		}
		CryptoAssetEntity entity = new CryptoAssetEntity();
		entity.setId(domain.getId());
		entity.setSymbol(domain.getSymbol());
		entity.setName(domain.getName());
		entity.setCoingeckoId(domain.getCoingeckoId());
		entity.setCurrentPriceUsd(domain.getCurrentPriceUsd());
		entity.setMarketCapRank(domain.getMarketCapRank());
		return entity;
	}
}
