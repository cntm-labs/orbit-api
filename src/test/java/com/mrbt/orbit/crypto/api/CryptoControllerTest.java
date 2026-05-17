package com.mrbt.orbit.crypto.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mrbt.orbit.crypto.api.mapper.CryptoDtoMapper;
import com.mrbt.orbit.crypto.core.model.CryptoAsset;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;
import com.mrbt.orbit.crypto.core.port.in.CryptoUseCase;

@WebMvcTest(CryptoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(CryptoDtoMapper.class)
class CryptoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CryptoUseCase cryptoUseCase;

	@Test
	void getAllAssets_ShouldReturnList() throws Exception {
		CryptoAsset asset = CryptoAsset.builder().id(UUID.randomUUID()).symbol("BTC").name("Bitcoin").build();
		when(cryptoUseCase.getAllTrackedAssets()).thenReturn(List.of(asset));

		mockMvc.perform(get("/api/v1/crypto/assets").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].symbol").value("BTC"));
	}

	@Test
	void getLatestSnapshot_ShouldReturnSnapshot() throws Exception {
		UUID userId = UUID.randomUUID();
		CryptoPortfolioSnapshot snapshot = CryptoPortfolioSnapshot.builder().id(UUID.randomUUID()).userId(userId)
				.totalValueUsd(new java.math.BigDecimal("5000.00")).build();
		when(cryptoUseCase.getLatestPortfolioSnapshot(userId)).thenReturn(snapshot);

		mockMvc.perform(get("/api/v1/crypto/portfolio/" + userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.totalValueUsd").value(5000.00));
	}

}
