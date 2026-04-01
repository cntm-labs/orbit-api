package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.model.enums.Frequency;
import com.mrbt.orbit.ledger.core.model.enums.RecurringTransactionStatus;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateRecurringTransactionServiceTest {

	@Mock
	private RecurringTransactionRepositoryPort repositoryPort;

	@InjectMocks
	private CreateRecurringTransactionService service;

	@Test
	void create_defaultsStatusToActive() {
		RecurringTransaction recurring = RecurringTransaction.builder().id(UUID.randomUUID()).userId(UUID.randomUUID())
				.accountId(UUID.randomUUID()).amount(new BigDecimal("100.00")).currencyCode("USD")
				.frequency(Frequency.MONTHLY).nextOccurrence(LocalDate.now()).build();

		when(repositoryPort.save(any(RecurringTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

		RecurringTransaction result = service.create(recurring);

		assertThat(result.getStatus()).isEqualTo(RecurringTransactionStatus.ACTIVE);
	}

	@Test
	void create_defaultsAutoConfirmToTrue() {
		RecurringTransaction recurring = RecurringTransaction.builder().id(UUID.randomUUID()).userId(UUID.randomUUID())
				.accountId(UUID.randomUUID()).amount(new BigDecimal("100.00")).currencyCode("USD")
				.frequency(Frequency.MONTHLY).nextOccurrence(LocalDate.now()).build();

		when(repositoryPort.save(any(RecurringTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

		RecurringTransaction result = service.create(recurring);

		assertThat(result.getAutoConfirm()).isTrue();
	}

}
