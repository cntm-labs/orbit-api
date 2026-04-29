package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.in.DeleteTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateTransactionService implements UpdateTransactionUseCase, DeleteTransactionUseCase {

	private final TransactionRepositoryPort transactionRepositoryPort;
	private final AccountRepositoryPort accountRepositoryPort;

	@Override
	@Transactional
	public Transaction updateTransaction(UUID transactionId, String description, UUID categoryId, Boolean isReviewed) {
		Transaction tx = transactionRepositoryPort.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "ID", transactionId));
		if (description != null) {
			tx.setDescription(description);
		}
		if (categoryId != null) {
			tx.setCategoryId(categoryId);
		}
		if (isReviewed != null) {
			tx.setIsReviewed(isReviewed);
		}
		return transactionRepositoryPort.save(tx);
	}

	@Override
	@Transactional
	public void voidTransaction(UUID transactionId) {
		Transaction tx = transactionRepositoryPort.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "ID", transactionId));
		if (tx.isCompleted()) {
			accountRepositoryPort.updateBalance(tx.getAccountId(), tx.getAmount().negate());
		}
		tx.voidTransaction();
		transactionRepositoryPort.save(tx);
	}
}
