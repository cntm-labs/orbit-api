package com.mrbt.orbit.audit.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.audit.core.port.out.AuditLogRepositoryPort;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {

	@Mock
	private AuditLogRepositoryPort repositoryPort;

	@InjectMocks
	private AuditLogService auditLogService;

	@Test
	void logAction_ShouldSaveAuditLog() {
		// Given
		AuditLog auditLog = AuditLog.builder().userId(UUID.randomUUID()).action(AuditAction.CREATE)
				.entityType("ACCOUNT").entityId(UUID.randomUUID()).build();
		when(repositoryPort.save(auditLog)).thenReturn(auditLog);

		// When
		AuditLog result = auditLogService.logAction(auditLog);

		// Then
		assertThat(result).isNotNull();
		verify(repositoryPort).save(auditLog);
	}

	@Test
	void getAuditLogsByUser_ShouldReturnPage() {
		// Given
		UUID userId = UUID.randomUUID();
		Pageable pageable = PageRequest.of(0, 10);
		Page<AuditLog> page = new PageImpl<>(List.of(AuditLog.builder().build()));
		when(repositoryPort.findByUserId(userId, pageable)).thenReturn(page);

		// When
		Page<AuditLog> result = auditLogService.getAuditLogsByUser(userId, pageable);

		// Then
		assertThat(result).hasSize(1);
		verify(repositoryPort).findByUserId(userId, pageable);
	}
}
