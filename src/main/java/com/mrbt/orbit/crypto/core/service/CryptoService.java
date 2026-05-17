package com.mrbt.orbit.crypto.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;
import com.mrbt.orbit.crypto.core.port.in.CryptoUseCase;
import com.mrbt.orbit.crypto.core.port.out.CryptoAssetRepositoryPort;
import com.mrbt.orbit.crypto.core.port.out.CryptoPortfolioSnapshotRepositoryPort;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoService implements CryptoUseCase {

	private final CryptoAssetRepositoryPort assetRepositoryPort;
	private final CryptoPortfolioSnapshotRepositoryPort snapshotRepositoryPort;
	private final AccountRepositoryPort accountRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public List<CryptoAsset> getAllTrackedAssets() {
		// In a real implementation, we would have a 'findAll' on the port
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public CryptoPortfolioSnapshot getLatestPortfolioSnapshot(UUID userId) {
		return snapshotRepositoryPort.findByUserIdOrderByCreatedAtDesc(userId).stream().findFirst().orElse(null);
	}

	@Override
	@Transactional
	public void refreshPrices() {
		log.info("Refreshing crypto prices from external feed...");
		// Implement external price fetch logic
	}

	@Override
	@Transactional
	public CryptoPortfolioSnapshot createSnapshot(UUID userId) {
		log.info("Creating portfolio snapshot for user: {}", userId);

		// 1. Get all accounts for user from Ledger module
		var accounts = accountRepositoryPort.findByUserId(userId);

		// 2. Filter for CRYPTO accounts and calculate total (Simplified for now)
		// Note: In a real system, we would map account symbols to current prices
		BigDecimal totalValue = accounts.stream().filter(a -> "CRYPTO".equals(a.getType().name()))
				.map(a -> a.getCurrentBalance()).reduce(BigDecimal.ZERO, BigDecimal::add);

		CryptoPortfolioSnapshot snapshot = CryptoPortfolioSnapshot.builder().userId(userId)
				.snapshotDate(LocalDate.now()).totalValueUsd(totalValue).holdingsJson("[]") // Placeholder for
																							// serialized holdings
				.build();

		return snapshotRepositoryPort.save(snapshot);
	}
}
