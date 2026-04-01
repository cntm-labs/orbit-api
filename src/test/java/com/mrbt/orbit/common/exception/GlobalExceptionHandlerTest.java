package com.mrbt.orbit.common.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mrbt.orbit.common.api.ApiResponse;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	void handleResourceNotFound_returns404() {
		ResourceNotFoundException ex = new ResourceNotFoundException("Account", "ID",
				"550e8400-e29b-41d4-a716-446655440000");

		ResponseEntity<ApiResponse<Void>> response = handler.handleResourceNotFound(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().success()).isFalse();
		assertThat(response.getBody().message()).contains("Account");
	}

	@Test
	void handleDuplicateResource_returns409() {
		DuplicateResourceException ex = new DuplicateResourceException("Account", "Name", "Savings");

		ResponseEntity<ApiResponse<Void>> response = handler.handleDuplicateResource(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().success()).isFalse();
		assertThat(response.getBody().message()).contains("Savings");
	}

	@Test
	void handleBadRequest_returns400() {
		BadRequestException ex = new BadRequestException("Amount cannot be zero");

		ResponseEntity<ApiResponse<Void>> response = handler.handleBadRequest(ex);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().success()).isFalse();
		assertThat(response.getBody().message()).isEqualTo("Amount cannot be zero");
	}

	@Test
	void handleInvalidStateTransition_shouldReturn409() {
		ResponseEntity<ApiResponse<Void>> response = handler.handleInvalidStateTransition(
				new InvalidStateTransitionException("Subscription", "CANCELLED", "ACTIVE"));
		assertThat(response.getStatusCode().value()).isEqualTo(409);
		assertThat(response.getBody().message()).contains("CANCELLED");
	}

	@Test
	void handleBusinessRuleViolation_shouldReturn422() {
		ResponseEntity<ApiResponse<Void>> response = handler
				.handleBusinessRuleViolation(new BusinessRuleViolationException("Amount cannot be zero"));
		assertThat(response.getStatusCode().value()).isEqualTo(422);
	}

	@Test
	void handleGenericException_shouldReturn500() {
		ResponseEntity<ApiResponse<Void>> response = handler.handleGenericException(new RuntimeException("unexpected"));
		assertThat(response.getStatusCode().value()).isEqualTo(500);
		assertThat(response.getBody().message()).isEqualTo("An unexpected error occurred");
	}

}
