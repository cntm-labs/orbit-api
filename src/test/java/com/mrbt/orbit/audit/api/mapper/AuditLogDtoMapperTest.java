package com.mrbt.orbit.audit.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.audit.api.response.AuditLogResponse;
import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.model.enums.AuditAction;

class AuditLogDtoMapperTest {

	private final AuditLogDtoMapper mapper = new AuditLogDtoMapper();

	@Test
	void toResponse_ShouldMapCorrectly() {
		AuditLog domain = AuditLog.builder().id(UUID.randomUUID()).userId(UUID.randomUUID()).action(AuditAction.UPDATE)
				.entityType("ACCOUNT").entityId(UUID.randomUUID()).changesJson("{}").ipAddress("127.0.0.1")
				.createdAt(OffsetDateTime.now()).build();

		AuditLogResponse response = mapper.toResponse(domain);

		assertThat(response).isNotNull();
		assertThat(response.action()).isEqualTo(domain.getAction());
		assertThat(response.entityType()).isEqualTo(domain.getEntityType());
		assertThat(response.ipAddress()).isEqualTo(domain.getIpAddress());
	}
}
