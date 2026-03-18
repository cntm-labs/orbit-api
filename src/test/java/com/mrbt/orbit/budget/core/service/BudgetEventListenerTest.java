package com.mrbt.orbit.budget.core.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.audit.core.model.Notification;
import com.mrbt.orbit.audit.core.port.in.CreateNotificationUseCase;
import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.ledger.core.model.TransactionCreatedEvent;

@ExtendWith(MockitoExtension.class)
class BudgetEventListenerTest {

	@Mock
	private BudgetRepositoryPort budgetRepositoryPort;

	@Mock
	private CreateNotificationUseCase createNotificationUseCase;

	@InjectMocks
	private BudgetEventListener budgetEventListener;

	@Test
	void onTransactionCreated_updatesSpentAmount() {
		UUID itemId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		BudgetItem item = BudgetItem.builder().id(itemId).budgetId(UUID.randomUUID()).categoryId(categoryId)
				.allocatedAmount(new BigDecimal("500")).spentAmount(new BigDecimal("100")).build();

		TransactionCreatedEvent event = new TransactionCreatedEvent(categoryId, new BigDecimal("-50"),
				Instant.parse("2026-03-15T10:00:00Z"));

		when(budgetRepositoryPort.findActiveItemsByCategoryIdAndDate(any(), any())).thenReturn(List.of(item));

		budgetEventListener.onTransactionCreated(event);

		verify(budgetRepositoryPort).updateSpentAmount(eq(itemId), eq(new BigDecimal("50")));
	}

	@Test
	void onTransactionCreated_sendsAlertWhenThresholdCrossed() {
		UUID itemId = UUID.randomUUID();
		UUID budgetId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();

		BudgetItem item = BudgetItem.builder().id(itemId).budgetId(budgetId).categoryId(categoryId)
				.allocatedAmount(new BigDecimal("100")).spentAmount(new BigDecimal("70")).alertThresholdPct(80).build();

		Budget budget = Budget.builder().id(budgetId).userId(userId).name("Monthly").build();

		TransactionCreatedEvent event = new TransactionCreatedEvent(categoryId, new BigDecimal("-20"),
				Instant.parse("2026-03-15T10:00:00Z"));

		when(budgetRepositoryPort.findActiveItemsByCategoryIdAndDate(any(), any())).thenReturn(List.of(item));
		when(budgetRepositoryPort.findById(budgetId)).thenReturn(Optional.of(budget));

		budgetEventListener.onTransactionCreated(event);

		verify(createNotificationUseCase).createNotification(any(Notification.class));
	}

	@Test
	void onTransactionCreated_doesNothingWhenNoMatchingItems() {
		UUID categoryId = UUID.randomUUID();
		TransactionCreatedEvent event = new TransactionCreatedEvent(categoryId, new BigDecimal("-30"),
				Instant.parse("2026-03-15T10:00:00Z"));

		when(budgetRepositoryPort.findActiveItemsByCategoryIdAndDate(any(), any())).thenReturn(List.of());

		budgetEventListener.onTransactionCreated(event);

		verify(budgetRepositoryPort, never()).updateSpentAmount(any(), any());
		verifyNoInteractions(createNotificationUseCase);
	}

}
