package com.mrbt.orbit.crypto.core.port.out;

import java.util.Optional;

import com.mrbt.orbit.crypto.core.model.CryptoAsset;

public interface CryptoAssetRepositoryPort {
	Optional<CryptoAsset> findBySymbol(String symbol);
	CryptoAsset save(CryptoAsset asset);
}
