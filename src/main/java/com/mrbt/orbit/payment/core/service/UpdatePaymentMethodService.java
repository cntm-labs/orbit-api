package com.mrbt.orbit.payment.core.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.payment.core.model.PaymentMethod;
import com.mrbt.orbit.payment.core.port.in.UpdatePaymentMethodUseCase;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePaymentMethodService implements UpdatePaymentMethodUseCase {

	private final PaymentMethodRepositoryPort repositoryPort;

	@Override
	@Transactional
	public PaymentMethod update(UUID id, Boolean isDefault) {
		PaymentMethod paymentMethod = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("PaymentMethod", "ID", id));
		if (isDefault != null) {
			paymentMethod.setIsDefault(isDefault);
		}
		return repositoryPort.save(paymentMethod);
	}

}
