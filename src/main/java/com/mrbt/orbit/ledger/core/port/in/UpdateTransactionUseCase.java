package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Transaction;

import java.util.UUID;

public interface UpdateTransactionUseCase extends UseCase {

	Transaction updateTransaction(UUID transactionId, String description, UUID categoryId, Boolean isReviewed);
}
