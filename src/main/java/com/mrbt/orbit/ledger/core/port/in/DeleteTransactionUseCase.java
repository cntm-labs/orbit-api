package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.UUID;

public interface DeleteTransactionUseCase extends UseCase {

	void voidTransaction(UUID transactionId);
}
