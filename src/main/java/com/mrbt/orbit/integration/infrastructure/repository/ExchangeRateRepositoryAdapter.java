package com.mrbt.orbit.integration.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.port.out.ExchangeRateRepositoryPort;
import com.mrbt.orbit.integration.infrastructure.mapper.ExchangeRateEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExchangeRateRepositoryAdapter implements ExchangeRateRepositoryPort {

	private final ExchangeRateRepository repository;
	private final ExchangeRateEntityMapper mapper;

	@Override
	public Optional<ExchangeRate> findLatest(String baseCurrency, String targetCurrency) {
		return repository.findFirstByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc(baseCurrency, targetCurrency)
				.map(mapper::toDomain);
	}

	@Override
	public ExchangeRate save(ExchangeRate exchangeRate) {
		return mapper.toDomain(repository.save(mapper.toEntity(exchangeRate)));
	}
}
