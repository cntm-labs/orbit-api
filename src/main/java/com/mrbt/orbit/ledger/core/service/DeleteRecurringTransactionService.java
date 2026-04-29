package com.mrbt.orbit.ledger.core.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.port.in.DeleteRecurringTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRecurringTransactionService implements DeleteRecurringTransactionUseCase {

	private final RecurringTransactionRepositoryPort repositoryPort;

	@Override
	@Transactional
	public void cancel(UUID id) {
		RecurringTransaction recurring = repositoryPort.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("RecurringTransaction", "ID", id));
		recurring.cancel();
		repositoryPort.save(recurring);
	}

}
