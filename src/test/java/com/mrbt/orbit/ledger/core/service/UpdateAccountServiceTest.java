package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdateAccountServiceTest {

	@Mock
	private AccountRepositoryPort accountRepositoryPort;

	@InjectMocks
	private UpdateAccountService updateAccountService;

	@Test
	void updateAccount_updatesName() {
		UUID accountId = UUID.randomUUID();
		Account account = Account.builder().id(accountId).name("Old Name").type(AccountType.BANK)
				.status(AccountStatus.ACTIVE).currentBalance(BigDecimal.ZERO).build();

		when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepositoryPort.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

		Account result = updateAccountService.updateAccount(accountId, "New Name");

		assertThat(result.getName()).isEqualTo("New Name");
		verify(accountRepositoryPort).save(account);
	}

	@Test
	void updateAccount_throwsWhenNotFound() {
		UUID accountId = UUID.randomUUID();
		when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateAccountService.updateAccount(accountId, "Name"))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void deleteAccount_setsStatusToClosed() {
		UUID accountId = UUID.randomUUID();
		Account account = Account.builder().id(accountId).name("Savings").type(AccountType.BANK)
				.status(AccountStatus.ACTIVE).build();

		when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepositoryPort.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

		updateAccountService.deleteAccount(accountId);

		assertThat(account.getStatus()).isEqualTo(AccountStatus.CLOSED);
		verify(accountRepositoryPort).save(account);
	}

	@Test
	void deleteAccount_throwsWhenNotFound() {
		UUID accountId = UUID.randomUUID();
		when(accountRepositoryPort.findById(accountId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateAccountService.deleteAccount(accountId))
				.isInstanceOf(ResourceNotFoundException.class);
	}

}
