package com.mrbt.orbit.ledger.core.port.out;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.model.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
	Transaction save(Transaction transaction);
	Optional<Transaction> findById(UUID id);
	PageResult<Transaction> findByAccountId(UUID accountId, int page, int size);
}
