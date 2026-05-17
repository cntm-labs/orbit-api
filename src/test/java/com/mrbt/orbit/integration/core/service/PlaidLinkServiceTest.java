package com.mrbt.orbit.integration.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import com.mrbt.orbit.integration.core.model.enums.PlaidLinkStatus;
import com.mrbt.orbit.integration.core.port.out.PlaidLinkRepositoryPort;

@ExtendWith(MockitoExtension.class)
class PlaidLinkServiceTest {

	@Mock
	private PlaidLinkRepositoryPort repositoryPort;

	@InjectMocks
	private PlaidLinkService plaidLinkService;

	@Test
	void createLink_ShouldSaveLink() {
		// Given
		UUID userId = UUID.randomUUID();
		String publicToken = "public-token";
		when(repositoryPort.save(any(PlaidLink.class))).thenAnswer(i -> i.getArguments()[0]);

		// When
		PlaidLink result = plaidLinkService.createLink(userId, publicToken);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getStatus()).isEqualTo(PlaidLinkStatus.ACTIVE);
		verify(repositoryPort).save(any(PlaidLink.class));
	}

	@Test
	void getLinksByUser_ShouldReturnList() {
		// Given
		UUID userId = UUID.randomUUID();
		PlaidLink link = PlaidLink.builder().userId(userId).build();
		when(repositoryPort.findByUserId(userId)).thenReturn(List.of(link));

		// When
		List<PlaidLink> result = plaidLinkService.getLinksByUser(userId);

		// Then
		assertThat(result).hasSize(1);
		verify(repositoryPort).findByUserId(userId);
	}
}
