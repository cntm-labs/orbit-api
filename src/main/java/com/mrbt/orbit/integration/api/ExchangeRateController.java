package com.mrbt.orbit.integration.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.integration.api.mapper.IntegrationDtoMapper;
import com.mrbt.orbit.integration.api.response.ExchangeRateResponse;
import com.mrbt.orbit.integration.core.port.in.ExchangeRateUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
@Tag(name = "Exchange Rates", description = "Endpoints for managing exchange rates")
public class ExchangeRateController {

	private final ExchangeRateUseCase exchangeRateUseCase;
	private final IntegrationDtoMapper mapper;

	@GetMapping("/{base}/{target}")
	@Operation(summary = "Get latest exchange rate")
	public ResponseEntity<ApiResponse<ExchangeRateResponse>> getLatestRate(@PathVariable String base,
			@PathVariable String target) {
		return exchangeRateUseCase.getLatestRate(base, target).map(mapper::toResponse).map(ApiResponse::success)
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/refresh")
	@Operation(summary = "Trigger rate refresh")
	public ResponseEntity<ApiResponse<Void>> refreshRates() {
		exchangeRateUseCase.refreshRates();
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
