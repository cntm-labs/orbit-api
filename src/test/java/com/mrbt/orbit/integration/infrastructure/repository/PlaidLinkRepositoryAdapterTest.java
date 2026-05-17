package com.mrbt.orbit.integration.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.infrastructure.entity.PlaidLinkEntity;
import com.mrbt.orbit.integration.infrastructure.mapper.PlaidLinkEntityMapper;

@ExtendWith(MockitoExtension.class)
class PlaidLinkRepositoryAdapterTest {

	@Mock
	private PlaidLinkRepository repository;

	@Mock
	private PlaidLinkEntityMapper mapper;

	@InjectMocks
	private PlaidLinkRepositoryAdapter adapter;

	@Test
	void findByUserId_ShouldWork() {
		UUID userId = UUID.randomUUID();
		PlaidLinkEntity entity = new PlaidLinkEntity();
		PlaidLink domain = PlaidLink.builder().build();

		when(repository.findByUserId(userId)).thenReturn(List.of(entity));
		when(mapper.toDomainList(List.of(entity))).thenReturn(List.of(domain));

		List<PlaidLink> result = adapter.findByUserId(userId);

		assertThat(result).hasSize(1);
		verify(repository).findByUserId(userId);
	}
}
