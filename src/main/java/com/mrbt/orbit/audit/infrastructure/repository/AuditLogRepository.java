package com.mrbt.orbit.audit.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.audit.infrastructure.entity.AuditLogEntity;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {

}
