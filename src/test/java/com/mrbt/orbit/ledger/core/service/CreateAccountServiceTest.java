package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.DuplicateResourceException;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateAccountServiceTest {

	@Mock
	private AccountRepositoryPort accountRepositoryPort;

	@InjectMocks
	private CreateAccountService createAccountService;

	@Test
	void createAccount_setsDefaultStatusAndBalance() {
		UUID userId = UUID.randomUUID();
		Account account = Account.builder().userId(userId).name("Savings").type(AccountType.BANK).currencyCode("USD")
				.build();

		when(accountRepositoryPort.existsByUserIdAndName(userId, "Savings")).thenReturn(false);
		when(accountRepositoryPort.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

		Account result = createAccountService.createAccount(account);

		assertThat(result.getStatus()).isEqualTo(AccountStatus.ACTIVE);
		assertThat(result.getCurrentBalance()).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	void createAccount_preservesExplicitStatusAndBalance() {
		UUID userId = UUID.randomUUID();
		Account account = Account.builder().userId(userId).name("Old Account").type(AccountType.CREDIT)
				.currencyCode("THB").status(AccountStatus.INACTIVE).currentBalance(new BigDecimal("1000.00")).build();

		when(accountRepositoryPort.existsByUserIdAndName(userId, "Old Account")).thenReturn(false);
		when(accountRepositoryPort.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

		Account result = createAccountService.createAccount(account);

		assertThat(result.getStatus()).isEqualTo(AccountStatus.INACTIVE);
		assertThat(result.getCurrentBalance()).isEqualByComparingTo("1000.00");
	}

	@Test
	void createAccount_throwsOnDuplicateName() {
		UUID userId = UUID.randomUUID();
		Account account = Account.builder().userId(userId).name("Duplicate").type(AccountType.BANK).currencyCode("USD")
				.build();

		when(accountRepositoryPort.existsByUserIdAndName(userId, "Duplicate")).thenReturn(true);

		assertThatThrownBy(() -> createAccountService.createAccount(account))
				.isInstanceOf(DuplicateResourceException.class).hasMessageContaining("Duplicate");

		verify(accountRepositoryPort, never()).save(any());
	}

	@Test
	void createAccount_delegatesToRepository() {
		UUID userId = UUID.randomUUID();
		Account account = Account.builder().userId(userId).name("Crypto").type(AccountType.CRYPTO).currencyCode("BTC")
				.build();

		when(accountRepositoryPort.existsByUserIdAndName(userId, "Crypto")).thenReturn(false);
		when(accountRepositoryPort.save(any(Account.class))).thenAnswer(inv -> {
			Account saved = inv.getArgument(0);
			saved.setId(UUID.randomUUID());
			return saved;
		});

		Account result = createAccountService.createAccount(account);

		ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
		verify(accountRepositoryPort).save(captor.capture());
		assertThat(captor.getValue().getName()).isEqualTo("Crypto");
		assertThat(result.getId()).isNotNull();
	}

}
