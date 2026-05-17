package com.mrbt.orbit.audit.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.port.in.AuditLogUseCase;
import com.mrbt.orbit.audit.core.port.out.AuditLogRepositoryPort;

import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogService implements AuditLogUseCase {

	private final AuditLogRepositoryPort repositoryPort;

	@Override
	@Transactional
	public AuditLog logAction(AuditLog auditLog) {
		return repositoryPort.save(auditLog);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditLog> getAuditLogsByUser(UUID userId, Pageable pageable) {
		return repositoryPort.findByUserId(userId, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AuditLog> getAuditLogsByEntity(String entityType, UUID entityId, Pageable pageable) {
		return repositoryPort.findByEntityTypeAndEntityId(entityType, entityId, pageable);
	}
}
