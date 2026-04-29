package com.mrbt.orbit.ledger.core.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.port.in.GetRecurringTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetRecurringTransactionService implements GetRecurringTransactionUseCase {

	private final RecurringTransactionRepositoryPort repositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<RecurringTransaction> findById(UUID id) {
		return repositoryPort.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<RecurringTransaction> findByUserId(UUID userId, int page, int size) {
		return repositoryPort.findByUserId(userId, page, size);
	}

}
