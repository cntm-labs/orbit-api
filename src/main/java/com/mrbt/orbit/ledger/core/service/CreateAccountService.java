package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.audit.core.annotation.Auditable;
import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.common.exception.DuplicateResourceException;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.port.in.CreateAccountUseCase;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {

	private final AccountRepositoryPort accountRepositoryPort;

	@Override
	@Transactional
	@Auditable(action = AuditAction.CREATE, entityType = "ACCOUNT")
	public Account createAccount(Account account) {
		if (accountRepositoryPort.existsByUserIdAndName(account.getUserId(), account.getName())) {
			throw new DuplicateResourceException("Account", "Name", account.getName());
		}

		return accountRepositoryPort.save(account);
	}
}
