package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.model.enums.Frequency;
import com.mrbt.orbit.ledger.core.port.out.RecurringTransactionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetRecurringTransactionServiceTest {

	@Mock
	private RecurringTransactionRepositoryPort repositoryPort;

	@InjectMocks
	private GetRecurringTransactionService service;

	@Test
	void findById_returnsRecurring() {
		UUID id = UUID.randomUUID();
		RecurringTransaction recurring = RecurringTransaction.builder().id(id).userId(UUID.randomUUID())
				.accountId(UUID.randomUUID()).amount(new BigDecimal("100.00")).currencyCode("USD")
				.frequency(Frequency.MONTHLY).nextOccurrence(LocalDate.now()).build();

		when(repositoryPort.findById(id)).thenReturn(Optional.of(recurring));

		Optional<RecurringTransaction> result = service.findById(id);

		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(id);
	}

	@Test
	void findByUserId_returnsPageResult() {
		UUID userId = UUID.randomUUID();
		RecurringTransaction recurring = RecurringTransaction.builder().id(UUID.randomUUID()).userId(userId)
				.accountId(UUID.randomUUID()).amount(new BigDecimal("100.00")).currencyCode("USD")
				.frequency(Frequency.MONTHLY).nextOccurrence(LocalDate.now()).build();
		PageResult<RecurringTransaction> page = new PageResult<>(List.of(recurring), 1L, 1, 0, 20);

		when(repositoryPort.findByUserId(userId, 0, 20)).thenReturn(page);

		PageResult<RecurringTransaction> result = service.findByUserId(userId, 0, 20);

		assertThat(result.content()).hasSize(1);
		assertThat(result.totalElements()).isEqualTo(1L);
	}

}
