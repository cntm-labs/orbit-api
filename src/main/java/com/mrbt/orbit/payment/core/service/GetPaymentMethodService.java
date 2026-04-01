package com.mrbt.orbit.payment.core.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.payment.core.model.PaymentMethod;
import com.mrbt.orbit.payment.core.port.in.GetPaymentMethodUseCase;
import com.mrbt.orbit.payment.core.port.out.PaymentMethodRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPaymentMethodService implements GetPaymentMethodUseCase {

	private final PaymentMethodRepositoryPort repositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<PaymentMethod> findById(UUID id) {
		return repositoryPort.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<PaymentMethod> findByUserId(UUID userId, int page, int size) {
		return repositoryPort.findByUserId(userId, page, size);
	}

}
