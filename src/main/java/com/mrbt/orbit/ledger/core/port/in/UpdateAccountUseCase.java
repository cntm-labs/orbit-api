package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Account;

import java.util.UUID;

public interface UpdateAccountUseCase extends UseCase {

	Account updateAccount(UUID accountId, String name);
}
