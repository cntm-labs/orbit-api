package com.mrbt.orbit.payment.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.model.enums.SubscriptionStatus;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdateSubscriptionServiceTest {

	@Mock
	private SubscriptionRepositoryPort repositoryPort;

	@InjectMocks
	private UpdateSubscriptionService updateSubscriptionService;

	@Test
	void update_setsName() {
		UUID id = UUID.randomUUID();
		Subscription sub = Subscription.builder().id(id).name("Netflix").amount(new BigDecimal("15.99")).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(sub));
		when(repositoryPort.save(any(Subscription.class))).thenAnswer(inv -> inv.getArgument(0));

		Subscription result = updateSubscriptionService.update(id, "Netflix Premium", null, null);

		assertThat(result.getName()).isEqualTo("Netflix Premium");
		verify(repositoryPort).save(sub);
	}

	@Test
	void togglePause_activeToPaused() {
		UUID id = UUID.randomUUID();
		Subscription sub = Subscription.builder().id(id).name("Netflix").status(SubscriptionStatus.ACTIVE).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(sub));
		when(repositoryPort.save(any(Subscription.class))).thenAnswer(inv -> inv.getArgument(0));

		Subscription result = updateSubscriptionService.togglePause(id);

		assertThat(result.getStatus()).isEqualTo(SubscriptionStatus.PAUSED);
	}

	@Test
	void togglePause_pausedToActive() {
		UUID id = UUID.randomUUID();
		Subscription sub = Subscription.builder().id(id).name("Netflix").status(SubscriptionStatus.PAUSED).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(sub));
		when(repositoryPort.save(any(Subscription.class))).thenAnswer(inv -> inv.getArgument(0));

		Subscription result = updateSubscriptionService.togglePause(id);

		assertThat(result.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
	}

}
