package com.mrbt.orbit.crypto.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;
import com.mrbt.orbit.crypto.core.port.out.CryptoPortfolioSnapshotRepositoryPort;
import com.mrbt.orbit.crypto.infrastructure.mapper.CryptoPortfolioSnapshotEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoPortfolioSnapshotRepositoryAdapter implements CryptoPortfolioSnapshotRepositoryPort {

	private final CryptoPortfolioSnapshotRepository repository;
	private final CryptoPortfolioSnapshotEntityMapper mapper;

	@Override
	public CryptoPortfolioSnapshot save(CryptoPortfolioSnapshot snapshot) {
		return mapper.toDomain(repository.save(mapper.toEntity(snapshot)));
	}

	@Override
	public List<CryptoPortfolioSnapshot> findByUserIdOrderByCreatedAtDesc(UUID userId) {
		return mapper.toDomainList(repository.findByUserIdOrderByCreatedAtDesc(userId));
	}
}
