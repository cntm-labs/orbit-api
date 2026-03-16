package com.mrbt.orbit.audit.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record NotificationResponse(UUID id, UUID userId, String type, String title, String message, String channel,
		Boolean isRead, OffsetDateTime createdAt) {
}
