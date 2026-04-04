package com.mrbt.orbit.payment.core.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.payment.core.port.in.DeletePaymentMethodUseCase;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePaymentMethodService implements DeletePaymentMethodUseCase {

	private final PaymentMethodRepositoryPort repositoryPort;

	@Override
	@Transactional
	public void delete(UUID id) {
		repositoryPort.softDelete(id);
	}

}
