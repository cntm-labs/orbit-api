package com.mrbt.orbit.security.api.response;

import lombok.Builder;
import java.util.UUID;
import java.time.OffsetDateTime;

@Builder
public record UserResponse(UUID id, String email, String firstName, String lastName, String baseCurrency,
		String timezone, String status, OffsetDateTime createdAt) {
}
