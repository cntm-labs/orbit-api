package com.mrbt.orbit.ledger.api.response;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CategoryResponse(UUID id, UUID userId, String name, String type, String icon, String color,
		Boolean isSystem, UUID parentCategoryId, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
