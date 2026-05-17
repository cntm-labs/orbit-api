package com.mrbt.orbit.integration.core.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.port.in.ExchangeRateUseCase;
import com.mrbt.orbit.integration.core.port.out.ExchangeRateRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService implements ExchangeRateUseCase {

	private final ExchangeRateRepositoryPort repositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<ExchangeRate> getLatestRate(String base, String target) {
		return repositoryPort.findLatest(base, target);
	}

	@Override
	@Transactional
	public void refreshRates() {
		// In a real implementation, this would call an external API (ECB,
		// OpenExchangeRates)
		log.info("Refreshing exchange rates from external source...");
		// Placeholder logic
	}
}
