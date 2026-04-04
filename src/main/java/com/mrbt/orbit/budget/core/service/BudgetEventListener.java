package com.mrbt.orbit.budget.core.service;

import java.time.ZoneOffset;
import java.util.List;

import com.mrbt.orbit.audit.core.model.Notification;
import com.mrbt.orbit.audit.core.model.enums.NotificationChannel;
import com.mrbt.orbit.audit.core.model.enums.NotificationType;
import com.mrbt.orbit.audit.core.port.in.CreateNotificationUseCase;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.ledger.core.model.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BudgetEventListener {

	private final BudgetRepositoryPort budgetRepositoryPort;
	private final CreateNotificationUseCase createNotificationUseCase;

	@EventListener
	@Transactional
	public void onTransactionCreated(TransactionCreatedEvent event) {
		List<BudgetItem> items = budgetRepositoryPort.findActiveItemsByCategoryIdAndDate(event.categoryId(),
				event.transactionDate().atZone(ZoneOffset.UTC).toLocalDate());

		for (BudgetItem item : items) {
			budgetRepositoryPort.updateSpentAmount(item.getId(), event.amount().abs());

			if (item.isAlertThresholdExceeded(event.amount().abs())) {
				budgetRepositoryPort.findById(item.getBudgetId())
						.ifPresent(budget -> createNotificationUseCase.createNotification(Notification.builder()
								.userId(budget.getUserId()).type(NotificationType.BUDGET_ALERT).title("Budget Alert")
								.message("You've exceeded your budget alert threshold for this category")
								.channel(NotificationChannel.IN_APP).isRead(false).build()));
			}
		}
	}
}
