package com.mrbt.orbit.integration.core.port.out;

import java.util.Optional;

import com.mrbt.orbit.integration.core.model.ExchangeRate;

public interface ExchangeRateRepositoryPort {
	Optional<ExchangeRate> findLatest(String baseCurrency, String targetCurrency);
	ExchangeRate save(ExchangeRate exchangeRate);
}
