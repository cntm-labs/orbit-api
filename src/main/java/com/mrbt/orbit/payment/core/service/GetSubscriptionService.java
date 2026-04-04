package com.mrbt.orbit.payment.core.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.port.in.GetSubscriptionUseCase;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetSubscriptionService implements GetSubscriptionUseCase {

	private final SubscriptionRepositoryPort repositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<Subscription> findById(UUID id) {
		return repositoryPort.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<Subscription> findByUserId(UUID userId, int page, int size) {
		return repositoryPort.findByUserId(userId, page, size);
	}

}
