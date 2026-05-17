package com.mrbt.orbit.integration.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.port.out.ExchangeRateRepositoryPort;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

	@Mock
	private ExchangeRateRepositoryPort repositoryPort;

	@InjectMocks
	private ExchangeRateService exchangeRateService;

	@Test
	void getLatestRate_ShouldReturnRate() {
		// Given
		String base = "USD";
		String target = "THB";
		ExchangeRate rate = ExchangeRate.builder().id(UUID.randomUUID()).baseCurrency(base).targetCurrency(target)
				.rate(new BigDecimal("35.50")).fetchedAt(OffsetDateTime.now()).build();

		when(repositoryPort.findLatest(base, target)).thenReturn(Optional.of(rate));

		// When
		Optional<ExchangeRate> result = exchangeRateService.getLatestRate(base, target);

		// Then
		assertThat(result).isPresent();
		assertThat(result.get().getRate()).isEqualByComparingTo("35.50");
		verify(repositoryPort).findLatest(base, target);
	}
}
