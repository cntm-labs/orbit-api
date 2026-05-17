package com.mrbt.orbit.integration.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.integration.infrastructure.entity.ExchangeRateEntity;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, UUID> {
	Optional<ExchangeRateEntity> findFirstByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc(String baseCurrency,
			String targetCurrency);
}
