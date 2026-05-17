package com.mrbt.orbit.audit.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.audit.api.response.AuditLogResponse;
import com.mrbt.orbit.audit.core.model.AuditLog;

@Component
public class AuditLogDtoMapper {

	public AuditLogResponse toResponse(AuditLog domain) {
		if (domain == null) {
			return null;
		}
		return new AuditLogResponse(domain.getId(), domain.getUserId(), domain.getAction(), domain.getEntityType(),
				domain.getEntityId(), domain.getChangesJson(), domain.getIpAddress(), domain.getCreatedAt());
	}
}
