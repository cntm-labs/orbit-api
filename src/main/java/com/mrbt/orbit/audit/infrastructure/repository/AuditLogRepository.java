package com.mrbt.orbit.audit.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.audit.infrastructure.entity.AuditLogEntity;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {
	Page<AuditLogEntity> findByUserId(UUID userId, Pageable pageable);
	Page<AuditLogEntity> findByEntityTypeAndEntityId(String entityType, UUID entityId, Pageable pageable);
}
