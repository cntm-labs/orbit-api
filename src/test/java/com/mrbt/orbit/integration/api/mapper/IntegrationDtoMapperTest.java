package com.mrbt.orbit.integration.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.integration.api.response.ExchangeRateResponse;
import com.mrbt.orbit.integration.api.response.PlaidLinkResponse;
import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.core.model.enums.PlaidLinkStatus;

class IntegrationDtoMapperTest {

	private final IntegrationDtoMapper mapper = new IntegrationDtoMapper();

	@Test
	void toResponse_ExchangeRate_ShouldMapCorrectly() {
		ExchangeRate domain = ExchangeRate.builder().id(UUID.randomUUID()).baseCurrency("USD").targetCurrency("THB")
				.rate(new BigDecimal("35.00")).source("ECB").fetchedAt(OffsetDateTime.now()).build();

		ExchangeRateResponse response = mapper.toResponse(domain);

		assertThat(response).isNotNull();
		assertThat(response.baseCurrency()).isEqualTo(domain.getBaseCurrency());
		assertThat(response.rate()).isEqualByComparingTo(domain.getRate());
	}

	@Test
	void toResponse_PlaidLink_ShouldMapCorrectly() {
		PlaidLink domain = PlaidLink.builder().id(UUID.randomUUID()).userId(UUID.randomUUID())
				.institutionName("Chase Bank").status(PlaidLinkStatus.ACTIVE).errorCode(null).build();

		PlaidLinkResponse response = mapper.toResponse(domain);

		assertThat(response).isNotNull();
		assertThat(response.institutionName()).isEqualTo(domain.getInstitutionName());
		assertThat(response.status()).isEqualTo(domain.getStatus());
	}
}
