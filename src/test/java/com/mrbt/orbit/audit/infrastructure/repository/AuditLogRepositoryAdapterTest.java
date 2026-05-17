package com.mrbt.orbit.audit.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.audit.core.model.AuditLog;
import com.mrbt.orbit.audit.infrastructure.entity.AuditLogEntity;
import com.mrbt.orbit.audit.infrastructure.mapper.AuditLogEntityMapper;

@ExtendWith(MockitoExtension.class)
class AuditLogRepositoryAdapterTest {

	@Mock
	private AuditLogRepository repository;

	@Mock
	private AuditLogEntityMapper mapper;

	@InjectMocks
	private AuditLogRepositoryAdapter adapter;

	@Test
	void save_ShouldWork() {
		AuditLog domain = AuditLog.builder().id(UUID.randomUUID()).build();
		AuditLogEntity entity = new AuditLogEntity();

		when(mapper.toEntity(domain)).thenReturn(entity);
		when(repository.save(entity)).thenReturn(entity);
		when(mapper.toDomain(entity)).thenReturn(domain);

		AuditLog result = adapter.save(domain);

		assertThat(result).isEqualTo(domain);
		verify(repository).save(entity);
	}
}
