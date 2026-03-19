package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.port.in.DeleteAccountUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateAccountUseCase;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateAccountService implements UpdateAccountUseCase, DeleteAccountUseCase {

	private final AccountRepositoryPort accountRepositoryPort;

	@Override
	@Transactional
	public Account updateAccount(UUID accountId, String name) {
		Account account = accountRepositoryPort.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "ID", accountId));
		if (name != null) {
			account.setName(name);
		}
		return accountRepositoryPort.save(account);
	}

	@Override
	@Transactional
	public void deleteAccount(UUID accountId) {
		Account account = accountRepositoryPort.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "ID", accountId));
		account.setStatus(AccountStatus.CLOSED);
		accountRepositoryPort.save(account);
	}
}
