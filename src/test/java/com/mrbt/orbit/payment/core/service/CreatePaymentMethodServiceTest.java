package com.mrbt.orbit.payment.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.payment.core.model.PaymentMethod;
import com.mrbt.orbit.payment.core.model.enums.PaymentMethodStatus;
import com.mrbt.orbit.payment.core.model.enums.PaymentProvider;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreatePaymentMethodServiceTest {

	@Mock
	private PaymentMethodRepositoryPort repositoryPort;

	@InjectMocks
	private CreatePaymentMethodService createPaymentMethodService;

	@Test
	void create_setsDefaultStatusAndIsDefault() {
		PaymentMethod pm = PaymentMethod.builder().userId(UUID.randomUUID()).provider(PaymentProvider.STRIPE).build();

		when(repositoryPort.save(any(PaymentMethod.class))).thenAnswer(inv -> {
			PaymentMethod saved = inv.getArgument(0);
			saved.setId(UUID.randomUUID());
			return saved;
		});

		PaymentMethod result = createPaymentMethodService.create(pm);

		assertThat(result.getStatus()).isEqualTo(PaymentMethodStatus.ACTIVE);
		assertThat(result.getIsDefault()).isFalse();
		verify(repositoryPort).save(pm);
	}

}
