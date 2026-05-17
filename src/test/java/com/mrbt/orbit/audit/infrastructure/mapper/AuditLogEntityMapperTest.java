package com.mrbt.orbit.audit.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.infrastructure.entity.AuditLogEntity;

class AuditLogEntityMapperTest {

	private final AuditLogEntityMapper mapper = new AuditLogEntityMapper();

	@Test
	void toDomain_ShouldMapCorrectly() {
		AuditLogEntity entity = new AuditLogEntity();
		entity.setId(UUID.randomUUID());
		entity.setEntityType("ACCOUNT");

		AuditLog domain = mapper.toDomain(entity);

		assertThat(domain).isNotNull();
		assertThat(domain.getEntityType()).isEqualTo(entity.getEntityType());
	}
}
