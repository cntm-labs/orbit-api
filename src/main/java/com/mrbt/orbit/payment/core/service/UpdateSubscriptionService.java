package com.mrbt.orbit.payment.core.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.payment.core.model.Subscription;
import com.mrbt.orbit.payment.core.port.in.UpdateSubscriptionUseCase;
import com.mrbt.orbit.payment.core.port.out.SubscriptionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateSubscriptionService implements UpdateSubscriptionUseCase {

	private final SubscriptionRepositoryPort repositoryPort;

	@Override
	@Transactional
	public Subscription update(UUID id, String name, BigDecimal amount, Integer reminderDaysBefore) {
		Subscription subscription = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "ID", id));
		if (name != null) {
			subscription.setName(name);
		}
		if (amount != null) {
			subscription.setAmount(amount);
		}
		if (reminderDaysBefore != null) {
			subscription.setReminderDaysBefore(reminderDaysBefore);
		}
		return repositoryPort.save(subscription);
	}

	@Override
	@Transactional
	public Subscription togglePause(UUID id) {
		Subscription subscription = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Subscription", "ID", id));
		subscription.togglePause();
		return repositoryPort.save(subscription);
	}

}
