package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.model.enums.Frequency;
import com.mrbt.orbit.ledger.core.model.enums.RecurringTransactionStatus;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeleteRecurringTransactionServiceTest {

	@Mock
	private RecurringTransactionRepositoryPort repositoryPort;

	@InjectMocks
	private DeleteRecurringTransactionService service;

	@Test
	void cancel_setsStatusToCancelled() {
		RecurringTransaction recurring = RecurringTransaction.builder().id(UUID.randomUUID()).userId(UUID.randomUUID())
				.accountId(UUID.randomUUID()).amount(new BigDecimal("100.00")).currencyCode("USD")
				.frequency(Frequency.MONTHLY).nextOccurrence(LocalDate.now()).build();
		UUID id = recurring.getId();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(recurring));
		when(repositoryPort.save(any(RecurringTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

		service.cancel(id);

		assertThat(recurring.getStatus()).isEqualTo(RecurringTransactionStatus.CANCELLED);
		verify(repositoryPort).save(recurring);
	}

	@Test
	void cancel_throwsWhenNotFound() {
		UUID id = UUID.randomUUID();

		when(repositoryPort.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.cancel(id)).isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("RecurringTransaction");
	}

}
