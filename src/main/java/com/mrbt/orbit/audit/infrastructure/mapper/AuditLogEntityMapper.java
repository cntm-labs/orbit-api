package com.mrbt.orbit.audit.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.infrastructure.entity.AuditLogEntity;

@Component
public class AuditLogEntityMapper extends AbstractNullSafeMapper<AuditLogEntity, AuditLog> {

	@Override
	public AuditLog toDomain(AuditLogEntity entity) {
		if (entity == null) {
			return null;
		}
		return AuditLog.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.userId(entity.getUserId()).action(entity.getAction()).entityType(entity.getEntityType())
				.entityId(entity.getEntityId()).changesJson(entity.getChangesJson()).ipAddress(entity.getIpAddress())
				.build();
	}

	@Override
	public AuditLogEntity toEntity(AuditLog domain) {
		if (domain == null) {
			return null;
		}
		AuditLogEntity entity = new AuditLogEntity();
		entity.setId(domain.getId());
		entity.setUserId(domain.getUserId());
		entity.setAction(domain.getAction());
		entity.setEntityType(domain.getEntityType());
		entity.setEntityId(domain.getEntityId());
		entity.setChangesJson(domain.getChangesJson());
		entity.setIpAddress(domain.getIpAddress());
		return entity;
	}
}
