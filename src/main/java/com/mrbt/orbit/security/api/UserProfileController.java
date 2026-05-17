package com.mrbt.orbit.security.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.security.api.mapper.UserProfileDtoMapper;
import com.mrbt.orbit.security.api.request.NotificationPreferenceRequest;
import com.mrbt.orbit.security.api.request.UserAddressRequest;
import com.mrbt.orbit.security.api.request.UserPreferenceRequest;
import com.mrbt.orbit.security.api.response.NotificationPreferenceResponse;
import com.mrbt.orbit.security.api.response.UserAddressResponse;
import com.mrbt.orbit.security.api.response.UserPreferenceResponse;
import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.core.port.in.UserProfileUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/{userId}/profile")
@RequiredArgsConstructor
@Tag(name = "User Profile", description = "Endpoints for managing user profile, addresses, and preferences")
public class UserProfileController {

	private final UserProfileUseCase userProfileUseCase;
	private final UserProfileDtoMapper mapper;

	@PostMapping("/addresses")
	@Operation(summary = "Add new address")
	public ResponseEntity<ApiResponse<UserAddressResponse>> addAddress(@PathVariable UUID userId,
			@Valid @RequestBody UserAddressRequest request) {
		UserAddress address = userProfileUseCase.addAddress(mapper.toDomain(userId, request));
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(address)));
	}

	@GetMapping("/addresses")
	@Operation(summary = "Get all user addresses")
	public ResponseEntity<ApiResponse<List<UserAddressResponse>>> getAddresses(@PathVariable UUID userId) {
		List<UserAddress> addresses = userProfileUseCase.getUserAddresses(userId);
		return ResponseEntity.ok(ApiResponse.success(addresses.stream().map(mapper::toResponse).toList()));
	}

	@PutMapping("/addresses/{addressId}")
	@Operation(summary = "Update address")
	public ResponseEntity<ApiResponse<UserAddressResponse>> updateAddress(@PathVariable UUID userId,
			@PathVariable UUID addressId, @Valid @RequestBody UserAddressRequest request) {
		UserAddress address = mapper.toDomain(userId, request);
		address.setId(addressId);
		UserAddress updated = userProfileUseCase.updateAddress(address);
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(updated)));
	}

	@DeleteMapping("/addresses/{addressId}")
	@Operation(summary = "Remove address")
	public ResponseEntity<ApiResponse<Void>> removeAddress(@PathVariable UUID userId, @PathVariable UUID addressId) {
		userProfileUseCase.removeAddress(addressId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@GetMapping("/preferences")
	@Operation(summary = "Get user preferences")
	public ResponseEntity<ApiResponse<UserPreferenceResponse>> getPreferences(@PathVariable UUID userId) {
		UserPreference preference = userProfileUseCase.getPreferences(userId);
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(preference)));
	}

	@PutMapping("/preferences")
	@Operation(summary = "Update user preferences")
	public ResponseEntity<ApiResponse<UserPreferenceResponse>> updatePreferences(@PathVariable UUID userId,
			@Valid @RequestBody UserPreferenceRequest request) {
		UserPreference preference = userProfileUseCase.updatePreferences(mapper.toDomain(userId, request));
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(preference)));
	}

	@GetMapping("/notifications")
	@Operation(summary = "Get notification settings")
	public ResponseEntity<ApiResponse<NotificationPreferenceResponse>> getNotificationSettings(
			@PathVariable UUID userId) {
		NotificationPreference preference = userProfileUseCase.getNotificationSettings(userId);
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(preference)));
	}

	@PutMapping("/notifications")
	@Operation(summary = "Update notification settings")
	public ResponseEntity<ApiResponse<NotificationPreferenceResponse>> updateNotificationSettings(
			@PathVariable UUID userId, @Valid @RequestBody NotificationPreferenceRequest request) {
		NotificationPreference preference = userProfileUseCase
				.updateNotificationSettings(mapper.toDomain(userId, request));
		return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(preference)));
	}
}
