package com.mrbt.orbit.crypto.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.port.out.CryptoAssetRepositoryPort;
import com.mrbt.orbit.crypto.infrastructure.mapper.CryptoAssetEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CryptoAssetRepositoryAdapter implements CryptoAssetRepositoryPort {

	private final CryptoAssetRepository repository;
	private final CryptoAssetEntityMapper mapper;

	@Override
	public Optional<CryptoAsset> findBySymbol(String symbol) {
		return repository.findBySymbol(symbol).map(mapper::toDomain);
	}

	@Override
	public CryptoAsset save(CryptoAsset asset) {
		return mapper.toDomain(repository.save(mapper.toEntity(asset)));
	}
}
