package com.mrbt.orbit.ledger.core.port.out;

import com.mrbt.orbit.ledger.core.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
	Transaction save(Transaction transaction);
	Optional<Transaction> findById(UUID id);
	List<Transaction> findByAccountId(UUID accountId);
}
