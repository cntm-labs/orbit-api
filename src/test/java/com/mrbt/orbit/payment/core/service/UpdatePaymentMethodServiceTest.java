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

import com.mrbt.orbit.payment.core.model.PaymentMethod;
import com.mrbt.orbit.payment.core.model.enums.PaymentProvider;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdatePaymentMethodServiceTest {

	@Mock
	private PaymentMethodRepositoryPort repositoryPort;

	@InjectMocks
	private UpdatePaymentMethodService updatePaymentMethodService;

	@Test
	void update_setsIsDefault() {
		UUID id = UUID.randomUUID();
		PaymentMethod pm = PaymentMethod.builder().id(id).isDefault(false).provider(PaymentProvider.STRIPE).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(pm));
		when(repositoryPort.save(any(PaymentMethod.class))).thenAnswer(inv -> inv.getArgument(0));

		PaymentMethod result = updatePaymentMethodService.update(id, true);

		assertThat(result.getIsDefault()).isTrue();
		verify(repositoryPort).save(pm);
	}

}
