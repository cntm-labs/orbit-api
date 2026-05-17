package com.mrbt.orbit.crypto.core.port.in;

import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;

public interface CryptoUseCase extends UseCase {
	List<CryptoAsset> getAllTrackedAssets();
	CryptoPortfolioSnapshot getLatestPortfolioSnapshot(UUID userId);
	void refreshPrices();
	CryptoPortfolioSnapshot createSnapshot(UUID userId);
}
