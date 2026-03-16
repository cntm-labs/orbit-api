package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetAccountServiceTest {

	@Mock
	private AccountRepositoryPort accountRepositoryPort;

	@InjectMocks
	private GetAccountService getAccountService;

	@Test
	void getAccountById_returnsAccount() {
		UUID accountId = UUID.randomUUID();
		Account account = Account.builder().id(accountId).name("Savings").build();

		when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(account));

		Optional<Account> result = getAccountService.getAccountById(accountId);

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Savings");
		verify(accountRepositoryPort).findById(accountId);
	}

	@Test
	void getAccountById_returnsEmptyWhenNotFound() {
		UUID accountId = UUID.randomUUID();
		when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.empty());

		Optional<Account> result = getAccountService.getAccountById(accountId);

		assertThat(result).isEmpty();
	}

	@Test
	void getAccountsByUserId_returnsList() {
		UUID userId = UUID.randomUUID();
		List<Account> accounts = List.of(Account.builder().name("A1").type(AccountType.BANK).build(),
				Account.builder().name("A2").type(AccountType.CREDIT).build());

		when(accountRepositoryPort.findByUserId(userId)).thenReturn(accounts);

		List<Account> result = getAccountService.getAccountsByUserId(userId);

		assertThat(result).hasSize(2);
		verify(accountRepositoryPort).findByUserId(userId);
	}

}
