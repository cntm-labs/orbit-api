package com.mrbt.orbit.integration.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.infrastructure.entity.ExchangeRateEntity;

class ExchangeRateEntityMapperTest {

	private final ExchangeRateEntityMapper mapper = new ExchangeRateEntityMapper();

	@Test
	void toDomain_ShouldMapCorrectly() {
		ExchangeRateEntity entity = new ExchangeRateEntity();
		entity.setId(UUID.randomUUID());
		entity.setBaseCurrency("USD");
		entity.setTargetCurrency("THB");
		entity.setRate(new BigDecimal("35.00"));
		entity.setFetchedAt(Instant.now());

		ExchangeRate domain = mapper.toDomain(entity);

		assertThat(domain).isNotNull();
		assertThat(domain.getBaseCurrency()).isEqualTo(entity.getBaseCurrency());
		assertThat(domain.getRate()).isEqualByComparingTo(entity.getRate());
	}
}
