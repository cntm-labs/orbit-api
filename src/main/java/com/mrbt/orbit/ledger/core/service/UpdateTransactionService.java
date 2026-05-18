package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.audit.core.annotation.Auditable;
import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.in.UpdateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateTransactionService implements UpdateTransactionUseCase {

	private final TransactionRepositoryPort transactionRepositoryPort;

	@Override
	@Transactional
	@Auditable(action = AuditAction.UPDATE, entityType = "TRANSACTION")
	public Transaction updateTransaction(UUID transactionId, String description, UUID categoryId, Boolean isReviewed) {
		Transaction existing = transactionRepositoryPort.findById(transactionId)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "ID", transactionId));

		existing.setDescription(description);
		existing.setCategoryId(categoryId);
		existing.setIsReviewed(isReviewed);

		return transactionRepositoryPort.save(existing);
	}
}
