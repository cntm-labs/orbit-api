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
import com.mrbt.orbit.payment.core.model.PaymentMethod;
import com.mrbt.orbit.payment.core.model.enums.PaymentProvider;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetPaymentMethodServiceTest {

	@Mock
	private PaymentMethodRepositoryPort repositoryPort;

	@InjectMocks
	private GetPaymentMethodService getPaymentMethodService;

	@Test
	void findById_returnsPaymentMethod() {
		UUID id = UUID.randomUUID();
		PaymentMethod pm = PaymentMethod.builder().id(id).provider(PaymentProvider.PAYPAL).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(pm));

		Optional<PaymentMethod> result = getPaymentMethodService.findById(id);

		assertThat(result).isPresent();
		assertThat(result.get().getProvider()).isEqualTo(PaymentProvider.PAYPAL);
	}

	@Test
	void findByUserId_returnsPage() {
		UUID userId = UUID.randomUUID();
		PaymentMethod pm = PaymentMethod.builder().userId(userId).provider(PaymentProvider.STRIPE).build();
		PageResult<PaymentMethod> page = new PageResult<>(List.of(pm), 1L, 1, 0, 20);

		when(repositoryPort.findByUserId(userId, 0, 20)).thenReturn(page);

		PageResult<PaymentMethod> result = getPaymentMethodService.findByUserId(userId, 0, 20);

		assertThat(result.content()).hasSize(1);
		assertThat(result.totalElements()).isEqualTo(1L);
	}

}
