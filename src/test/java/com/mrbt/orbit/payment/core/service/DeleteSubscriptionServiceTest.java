package com.mrbt.orbit.payment.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class DeleteSubscriptionServiceTest {

	@Mock
	private SubscriptionRepositoryPort repositoryPort;

	@InjectMocks
	private DeleteSubscriptionService deleteSubscriptionService;

	@Test
	void delete_setsStatusAndSoftDeletes() {
		UUID id = UUID.randomUUID();
		Subscription sub = Subscription.builder().id(id).name("Netflix").status(SubscriptionStatus.ACTIVE).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(sub));
		when(repositoryPort.save(any(Subscription.class))).thenAnswer(inv -> inv.getArgument(0));

		deleteSubscriptionService.delete(id);

		assertThat(sub.getStatus()).isEqualTo(SubscriptionStatus.CANCELLED);
		verify(repositoryPort).save(sub);
		verify(repositoryPort).softDelete(id);
	}

}
