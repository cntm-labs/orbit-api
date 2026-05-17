package com.mrbt.orbit.integration.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.integration.api.mapper.IntegrationDtoMapper;
import com.mrbt.orbit.integration.api.request.CreatePlaidLinkRequest;
import com.mrbt.orbit.integration.api.response.PlaidLinkResponse;
import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.core.port.in.PlaidLinkUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plaid")
@RequiredArgsConstructor
@Tag(name = "Plaid Integration", description = "Endpoints for bank connectivity via Plaid")
public class PlaidLinkController {

	private final PlaidLinkUseCase plaidLinkUseCase;
	private final IntegrationDtoMapper mapper;

	@PostMapping("/link")
	@Operation(summary = "Create Plaid link")
	public ResponseEntity<ApiResponse<PlaidLinkResponse>> createLink(
			@Valid @RequestBody CreatePlaidLinkRequest request) {
		PlaidLink link = plaidLinkUseCase.createLink(request.userId(), request.publicToken());
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(link)));
	}

	@GetMapping("/links/user/{userId}")
	@Operation(summary = "List connected banks for a user")
	public ResponseEntity<ApiResponse<List<PlaidLinkResponse>>> getLinksByUser(@PathVariable UUID userId) {
		List<PlaidLink> links = plaidLinkUseCase.getLinksByUser(userId);
		return ResponseEntity.ok(ApiResponse.success(links.stream().map(mapper::toResponse).toList()));
	}

	@PostMapping("/links/{linkId}/sync")
	@Operation(summary = "Trigger transaction sync")
	public ResponseEntity<ApiResponse<Void>> syncTransactions(@PathVariable UUID linkId) {
		plaidLinkUseCase.syncTransactions(linkId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@DeleteMapping("/links/{linkId}")
	@Operation(summary = "Disconnect bank link")
	public ResponseEntity<ApiResponse<Void>> disconnectLink(@PathVariable UUID linkId) {
		plaidLinkUseCase.disconnectLink(linkId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
