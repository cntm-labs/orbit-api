package com.mrbt.orbit.budget.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.budget.core.model.enums.BudgetPeriodType;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.common.exception.BadRequestException;

@ExtendWith(MockitoExtension.class)
class CreateBudgetServiceTest {

	@Mock
	private BudgetRepositoryPort budgetRepositoryPort;

	@InjectMocks
	private CreateBudgetService createBudgetService;

	@Test
	void createBudget_calculatesTotal() {
		BudgetItem item1 = BudgetItem.builder().categoryId(UUID.randomUUID()).allocatedAmount(new BigDecimal("100"))
				.build();
		BudgetItem item2 = BudgetItem.builder().categoryId(UUID.randomUUID()).allocatedAmount(new BigDecimal("200"))
				.build();

		Budget budget = Budget.builder().userId(UUID.randomUUID()).name("Monthly").periodType(BudgetPeriodType.MONTHLY)
				.startDate(LocalDate.of(2026, 3, 1)).endDate(LocalDate.of(2026, 3, 31)).items(List.of(item1, item2))
				.build();

		when(budgetRepositoryPort.save(any(Budget.class))).thenAnswer(inv -> inv.getArgument(0));

		Budget result = createBudgetService.createBudget(budget);

		assertThat(result.getTotalAmount()).isEqualByComparingTo(new BigDecimal("300"));
		verify(budgetRepositoryPort).save(budget);
	}

	@Test
	void createBudget_defaultsStatusToActive() {
		Budget budget = Budget.builder().userId(UUID.randomUUID()).name("Q1").periodType(BudgetPeriodType.QUARTERLY)
				.startDate(LocalDate.of(2026, 1, 1)).endDate(LocalDate.of(2026, 3, 31)).items(List.of()).build();

		when(budgetRepositoryPort.save(any(Budget.class))).thenAnswer(inv -> inv.getArgument(0));

		Budget result = createBudgetService.createBudget(budget);

		assertThat(result.getStatus()).isEqualTo(BudgetStatus.ACTIVE);
	}

	@Test
	void createBudget_throwsWhenEndDateNotAfterStartDate() {
		LocalDate sameDate = LocalDate.of(2026, 3, 15);
		Budget budget = Budget.builder().userId(UUID.randomUUID()).name("Bad").periodType(BudgetPeriodType.CUSTOM)
				.startDate(sameDate).endDate(sameDate).items(List.of()).build();

		assertThatThrownBy(() -> createBudgetService.createBudget(budget)).isInstanceOf(BadRequestException.class)
				.hasMessageContaining("End date must be after start date");

		verify(budgetRepositoryPort, never()).save(any());
	}

}
