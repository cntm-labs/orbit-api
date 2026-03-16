package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Transaction;

public interface CreateTransactionUseCase extends UseCase {
	Transaction createTransaction(Transaction transaction);
}
