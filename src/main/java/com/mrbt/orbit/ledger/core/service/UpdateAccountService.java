package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.audit.core.annotation.Auditable;
import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.port.in.UpdateAccountUseCase;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAccountService implements UpdateAccountUseCase {

	private final AccountRepositoryPort accountRepositoryPort;

	@Override
	@Transactional
	@Auditable(action = AuditAction.UPDATE, entityType = "ACCOUNT")
	public Account updateAccount(UUID accountId, String name) {
		Account existing = accountRepositoryPort.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "ID", accountId));

		existing.setName(name);

		return accountRepositoryPort.save(existing);
	}
}
