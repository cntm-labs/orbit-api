package com.mrbt.orbit.payment.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetSubscriptionServiceTest {

	@Mock
	private SubscriptionRepositoryPort repositoryPort;

	@InjectMocks
	private GetSubscriptionService getSubscriptionService;

	@Test
	void findById_returnsSubscription() {
		UUID id = UUID.randomUUID();
		Subscription sub = Subscription.builder().id(id).name("Spotify").build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(sub));

		Optional<Subscription> result = getSubscriptionService.findById(id);

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Spotify");
	}

	@Test
	void findByUserId_returnsPage() {
		UUID userId = UUID.randomUUID();
		Subscription sub = Subscription.builder().userId(userId).name("Netflix").build();
		PageResult<Subscription> page = new PageResult<>(List.of(sub), 1L, 1, 0, 20);

		when(repositoryPort.findByUserId(userId, 0, 20)).thenReturn(page);

		PageResult<Subscription> result = getSubscriptionService.findByUserId(userId, 0, 20);

		assertThat(result.content()).hasSize(1);
	}

}
