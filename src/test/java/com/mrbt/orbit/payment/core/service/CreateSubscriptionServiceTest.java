package com.mrbt.orbit.payment.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.model.enums.BillingCycle;
import com.mrbt.orbit.payment.core.model.enums.SubscriptionStatus;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateSubscriptionServiceTest {

	@Mock
	private SubscriptionRepositoryPort repositoryPort;

	@InjectMocks
	private CreateSubscriptionService createSubscriptionService;

	@Test
	void create_setsDefaultStatus() {
		Subscription sub = Subscription.builder().userId(UUID.randomUUID()).name("Netflix")
				.amount(new BigDecimal("15.99")).billingCycle(BillingCycle.MONTHLY)
				.nextBillingDate(LocalDate.of(2026, 4, 1)).build();

		when(repositoryPort.save(any(Subscription.class))).thenAnswer(inv -> {
			Subscription saved = inv.getArgument(0);
			saved.setId(UUID.randomUUID());
			return saved;
		});

		Subscription result = createSubscriptionService.create(sub);

		assertThat(result.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
		verify(repositoryPort).save(sub);
	}

}
