package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.UUID;

public interface DeleteAccountUseCase extends UseCase {

	void deleteAccount(UUID accountId);
}
