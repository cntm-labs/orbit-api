package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
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
class UpdateRecurringTransactionServiceTest {

	@Mock
	private RecurringTransactionRepositoryPort repositoryPort;

	@InjectMocks
	private UpdateRecurringTransactionService service;

	private RecurringTransaction buildRecurring() {
		return RecurringTransaction.builder().id(UUID.randomUUID()).userId(UUID.randomUUID())
				.accountId(UUID.randomUUID()).amount(new BigDecimal("100.00")).currencyCode("USD")
				.description("Monthly rent").frequency(Frequency.MONTHLY).nextOccurrence(LocalDate.now()).build();
	}

	@Test
	void update_updatesDescription() {
		RecurringTransaction recurring = buildRecurring();
		UUID id = recurring.getId();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(recurring));
		when(repositoryPort.save(any(RecurringTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

		RecurringTransaction result = service.update(id, "Updated description", null, null);

		assertThat(result.getDescription()).isEqualTo("Updated description");
	}

	@Test
	void update_throwsWhenNotFound() {
		UUID id = UUID.randomUUID();

		when(repositoryPort.findById(id)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> service.update(id, "desc", null, null)).isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("RecurringTransaction");
	}

	@Test
	void togglePause_activeToPaused() {
		RecurringTransaction recurring = buildRecurring();
		recurring.setStatus(RecurringTransactionStatus.ACTIVE);
		UUID id = recurring.getId();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(recurring));
		when(repositoryPort.save(any(RecurringTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

		RecurringTransaction result = service.togglePause(id);

		assertThat(result.getStatus()).isEqualTo(RecurringTransactionStatus.PAUSED);
	}

	@Test
	void togglePause_pausedToActive() {
		RecurringTransaction recurring = buildRecurring();
		recurring.setStatus(RecurringTransactionStatus.PAUSED);
		UUID id = recurring.getId();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(recurring));
		when(repositoryPort.save(any(RecurringTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

		RecurringTransaction result = service.togglePause(id);

		assertThat(result.getStatus()).isEqualTo(RecurringTransactionStatus.ACTIVE);
	}

}
