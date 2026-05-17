package com.mrbt.orbit.crypto.core.port.out;

import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;

public interface CryptoPortfolioSnapshotRepositoryPort {
	CryptoPortfolioSnapshot save(CryptoPortfolioSnapshot snapshot);
	List<CryptoPortfolioSnapshot> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
