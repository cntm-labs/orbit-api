package com.mrbt.orbit.ledger.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.port.in.CreateRecurringTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateRecurringTransactionService implements CreateRecurringTransactionUseCase {

	private final RecurringTransactionRepositoryPort repositoryPort;

	@Override
	@Transactional
	public RecurringTransaction create(RecurringTransaction recurring) {
		recurring.applyDefaults();
		return repositoryPort.save(recurring);
	}

}
