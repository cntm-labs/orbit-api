package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.audit.core.annotation.Auditable;
import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.TransactionCreatedEvent;
import com.mrbt.orbit.ledger.core.port.in.CreateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTransactionService implements CreateTransactionUseCase {

	private final TransactionRepositoryPort transactionRepositoryPort;
	private final AccountRepositoryPort accountRepositoryPort;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	@Auditable(action = AuditAction.CREATE, entityType = "TRANSACTION")
	public Transaction createTransaction(Transaction transaction) {
		// 1. Validate associated account exists
		accountRepositoryPort.findById(transaction.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account", "ID", transaction.getAccountId()));

		// 2. Perform atomic balance update in Ledger
		accountRepositoryPort.updateBalance(transaction.getAccountId(), transaction.getAmount());

		// 3. Save the transaction
		Transaction saved = transactionRepositoryPort.save(transaction);

		// 4. Publish event for cross-module sync (e.g., Budget, Audit)
		if (saved.getCategoryId() != null && saved.isCompleted()) {
			eventPublisher.publishEvent(
					new TransactionCreatedEvent(saved.getCategoryId(), saved.getAmount(), saved.getTransactionDate()));
		}

		return saved;
	}
}
