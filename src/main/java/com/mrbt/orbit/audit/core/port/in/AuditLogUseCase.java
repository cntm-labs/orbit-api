package com.mrbt.orbit.audit.core.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.UUID;

public interface AuditLogUseCase extends UseCase {
	AuditLog logAction(AuditLog auditLog);
	Page<AuditLog> getAuditLogsByUser(UUID userId, Pageable pageable);
	Page<AuditLog> getAuditLogsByEntity(String entityType, UUID entityId, Pageable pageable);
}
