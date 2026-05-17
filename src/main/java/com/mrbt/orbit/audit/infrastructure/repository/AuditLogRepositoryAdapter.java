package com.mrbt.orbit.audit.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.port.out.AuditLogRepositoryPort;
import com.mrbt.orbit.audit.infrastructure.mapper.AuditLogEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuditLogRepositoryAdapter implements AuditLogRepositoryPort {

	private final AuditLogRepository repository;
	private final AuditLogEntityMapper mapper;

	@Override
	public AuditLog save(AuditLog auditLog) {
		return mapper.toDomain(repository.save(mapper.toEntity(auditLog)));
	}

	@Override
	public Page<AuditLog> findByUserId(UUID userId, Pageable pageable) {
		return repository.findByUserId(userId, pageable).map(mapper::toDomain);
	}

	@Override
	public Page<AuditLog> findByEntityTypeAndEntityId(String entityType, UUID entityId, Pageable pageable) {
		return repository.findByEntityTypeAndEntityId(entityType, entityId, pageable).map(mapper::toDomain);
	}
}
