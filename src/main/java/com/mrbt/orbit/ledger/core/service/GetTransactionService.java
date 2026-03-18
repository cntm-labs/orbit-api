package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.in.GetTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetTransactionService implements GetTransactionUseCase {

	private final TransactionRepositoryPort transactionRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<Transaction> getTransactionById(UUID transactionId) {
		return transactionRepositoryPort.findById(transactionId);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<Transaction> getTransactionsByAccountId(UUID accountId, int page, int size) {
		return transactionRepositoryPort.findByAccountId(accountId, page, size);
	}
}
