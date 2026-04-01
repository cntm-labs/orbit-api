package com.mrbt.orbit.payment.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.payment.core.model.PaymentMethod;
import com.mrbt.orbit.payment.core.port.in.CreatePaymentMethodUseCase;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePaymentMethodService implements CreatePaymentMethodUseCase {

	private final PaymentMethodRepositoryPort repositoryPort;

	@Override
	@Transactional
	public PaymentMethod create(PaymentMethod paymentMethod) {
		paymentMethod.applyDefaults();
		return repositoryPort.save(paymentMethod);
	}

}
