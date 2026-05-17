package com.mrbt.orbit.crypto.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;
import com.mrbt.orbit.crypto.infrastructure.entity.CryptoPortfolioSnapshotEntity;

@Component
public class CryptoPortfolioSnapshotEntityMapper
		extends
			AbstractNullSafeMapper<CryptoPortfolioSnapshotEntity, CryptoPortfolioSnapshot> {

	@Override
	public CryptoPortfolioSnapshot toDomain(CryptoPortfolioSnapshotEntity entity) {
		if (entity == null) {
			return null;
		}
		return CryptoPortfolioSnapshot.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.userId(entity.getUserId()).snapshotDate(entity.getSnapshotDate())
				.totalValueUsd(entity.getTotalValueUsd()).holdingsJson(entity.getHoldingsJson()).build();
	}

	@Override
	public CryptoPortfolioSnapshotEntity toEntity(CryptoPortfolioSnapshot domain) {
		if (domain == null) {
			return null;
		}
		CryptoPortfolioSnapshotEntity entity = new CryptoPortfolioSnapshotEntity();
		entity.setId(domain.getId());
		entity.setUserId(domain.getUserId());
		entity.setSnapshotDate(domain.getSnapshotDate());
		entity.setTotalValueUsd(domain.getTotalValueUsd());
		entity.setHoldingsJson(domain.getHoldingsJson());
		return entity;
	}
}
