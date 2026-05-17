package com.mrbt.orbit.crypto.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.crypto.api.mapper.CryptoDtoMapper;
import com.mrbt.orbit.crypto.api.response.CryptoAssetResponse;
import com.mrbt.orbit.crypto.api.response.CryptoPortfolioSnapshotResponse;
import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;
import com.mrbt.orbit.crypto.core.port.in.CryptoUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/crypto")
@RequiredArgsConstructor
@Tag(name = "Crypto", description = "Endpoints for cryptocurrency asset tracking")
public class CryptoController {

	private final CryptoUseCase cryptoUseCase;
	private final CryptoDtoMapper mapper;

	@GetMapping("/assets")
	@Operation(summary = "List all tracked crypto assets")
	public ResponseEntity<ApiResponse<List<CryptoAssetResponse>>> getAllAssets() {
		return ResponseEntity
				.ok(ApiResponse.success(cryptoUseCase.getAllTrackedAssets().stream().map(mapper::toResponse).toList()));
	}

	@GetMapping("/portfolio/{userId}")
	@Operation(summary = "Get latest portfolio snapshot for a user")
	public ResponseEntity<ApiResponse<CryptoPortfolioSnapshotResponse>> getLatestSnapshot(@PathVariable UUID userId) {
		CryptoPortfolioSnapshot snapshot = cryptoUseCase.getLatestPortfolioSnapshot(userId);
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(snapshot)));
	}

	@PostMapping("/assets/refresh")
	@Operation(summary = "Refresh crypto prices")
	public ResponseEntity<ApiResponse<Void>> refreshPrices() {
		cryptoUseCase.refreshPrices();
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@PostMapping("/portfolio/{userId}/snapshot")
	@Operation(summary = "Create manual portfolio snapshot")
	public ResponseEntity<ApiResponse<CryptoPortfolioSnapshotResponse>> createSnapshot(@PathVariable UUID userId) {
		CryptoPortfolioSnapshot snapshot = cryptoUseCase.createSnapshot(userId);
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(snapshot)));
	}
}
