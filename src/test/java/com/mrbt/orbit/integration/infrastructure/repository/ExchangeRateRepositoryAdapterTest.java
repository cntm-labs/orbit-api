package com.mrbt.orbit.integration.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.infrastructure.entity.ExchangeRateEntity;
import com.mrbt.orbit.integration.infrastructure.mapper.ExchangeRateEntityMapper;

@ExtendWith(MockitoExtension.class)
class ExchangeRateRepositoryAdapterTest {

	@Mock
	private ExchangeRateRepository repository;

	@Mock
	private ExchangeRateEntityMapper mapper;

	@InjectMocks
	private ExchangeRateRepositoryAdapter adapter;

	@Test
	void findLatest_ShouldWork() {
		String base = "USD";
		String target = "THB";
		ExchangeRateEntity entity = new ExchangeRateEntity();
		ExchangeRate domain = ExchangeRate.builder().build();

		when(repository.findFirstByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc(base, target))
				.thenReturn(Optional.of(entity));
		when(mapper.toDomain(entity)).thenReturn(domain);

		Optional<ExchangeRate> result = adapter.findLatest(base, target);

		assertThat(result).isPresent();
		verify(repository).findFirstByBaseCurrencyAndTargetCurrencyOrderByFetchedAtDesc(base, target);
	}
}
