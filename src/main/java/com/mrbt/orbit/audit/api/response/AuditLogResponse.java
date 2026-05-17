package com.mrbt.orbit.audit.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.mrbt.orbit.audit.core.model.enums.AuditAction;

public record AuditLogResponse(UUID id, UUID userId, AuditAction action, String entityType, UUID entityId,
		String changesJson, String ipAddress, OffsetDateTime createdAt) {
}
