package com.mrbt.orbit.payment.core.service;

import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeletePaymentMethodServiceTest {

	@Mock
	private PaymentMethodRepositoryPort repositoryPort;

	@InjectMocks
	private DeletePaymentMethodService deletePaymentMethodService;

	@Test
	void delete_callsSoftDelete() {
		UUID id = UUID.randomUUID();

		deletePaymentMethodService.delete(id);

		verify(repositoryPort).softDelete(id);
	}

}
