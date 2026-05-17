package com.mrbt.orbit.integration.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mrbt.orbit.integration.api.mapper.IntegrationDtoMapper;
import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.port.in.ExchangeRateUseCase;

@WebMvcTest(ExchangeRateController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(IntegrationDtoMapper.class)
class ExchangeRateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ExchangeRateUseCase exchangeRateUseCase;

	@Test
	void getLatestRate_ShouldReturnRate() throws Exception {
		String base = "USD";
		String target = "THB";
		ExchangeRate rate = ExchangeRate.builder().id(UUID.randomUUID()).baseCurrency(base).targetCurrency(target)
				.rate(new BigDecimal("35.00")).fetchedAt(OffsetDateTime.now()).build();

		when(exchangeRateUseCase.getLatestRate(base, target)).thenReturn(Optional.of(rate));

		mockMvc.perform(get("/api/v1/exchange-rates/" + base + "/" + target).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.rate").value(35.00));
	}
}
