package com.mrbt.orbit.security.api.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record UserAddressRequest(UUID id, @NotBlank String label, @NotBlank String addressLine1, String addressLine2,
		@NotBlank String city, String state, @NotBlank String postalCode, @NotBlank String countryCode,
		Boolean isDefault) {
}
