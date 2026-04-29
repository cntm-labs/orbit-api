package com.mrbt.orbit.payment.core.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.port.in.DeleteSubscriptionUseCase;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteSubscriptionService implements DeleteSubscriptionUseCase {

	private final SubscriptionRepositoryPort repositoryPort;

	@Override
	@Transactional
	public void delete(UUID id) {
		Subscription subscription = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "ID", id));
		subscription.cancel();
		repositoryPort.save(subscription);
		repositoryPort.softDelete(id);
	}

}
