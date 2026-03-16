package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetTransactionUseCase extends UseCase {
	Optional<Transaction> getTransactionById(UUID transactionId);
	List<Transaction> getTransactionsByAccountId(UUID accountId);
}
