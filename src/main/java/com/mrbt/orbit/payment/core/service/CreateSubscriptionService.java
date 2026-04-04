package com.mrbt.orbit.payment.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.port.in.CreateSubscriptionUseCase;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateSubscriptionService implements CreateSubscriptionUseCase {

	private final SubscriptionRepositoryPort repositoryPort;

	@Override
	@Transactional
	public Subscription create(Subscription subscription) {
		subscription.applyDefaults();
		return repositoryPort.save(subscription);
	}

}
