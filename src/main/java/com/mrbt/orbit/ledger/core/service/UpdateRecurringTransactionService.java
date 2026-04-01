package com.mrbt.orbit.ledger.core.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.port.in.UpdateRecurringTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateRecurringTransactionService implements UpdateRecurringTransactionUseCase {

	private final RecurringTransactionRepositoryPort repositoryPort;

	@Override
	@Transactional
	public RecurringTransaction update(UUID id, String description, BigDecimal amount, UUID categoryId) {
		RecurringTransaction recurring = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction", "ID", id));
		if (description != null) {
			recurring.setDescription(description);
		}
		if (amount != null) {
			recurring.setAmount(amount);
		}
		if (categoryId != null) {
			recurring.setCategoryId(categoryId);
		}
		return repositoryPort.save(recurring);
	}

	@Override
	@Transactional
	public RecurringTransaction togglePause(UUID id) {
		RecurringTransaction recurring = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction", "ID", id));
		recurring.togglePause();
		return repositoryPort.save(recurring);
	}

}
