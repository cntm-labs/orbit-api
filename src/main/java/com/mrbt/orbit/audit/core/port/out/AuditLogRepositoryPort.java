package com.mrbt.orbit.audit.core.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mrbt.orbit.audit.core.model.AuditLog;

import java.util.UUID;

public interface AuditLogRepositoryPort {
	AuditLog save(AuditLog auditLog);
	Page<AuditLog> findByUserId(UUID userId, Pageable pageable);
	Page<AuditLog> findByEntityTypeAndEntityId(String entityType, UUID entityId, Pageable pageable);
}
