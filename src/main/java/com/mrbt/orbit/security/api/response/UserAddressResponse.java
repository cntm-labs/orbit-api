package com.mrbt.orbit.security.api.response;

import java.util.UUID;

public record UserAddressResponse(UUID id, String label, String addressLine1, String addressLine2, String city,
		String state, String postalCode, String countryCode, Boolean isDefault) {
}
