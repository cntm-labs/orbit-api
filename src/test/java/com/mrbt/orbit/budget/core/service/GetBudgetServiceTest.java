package com.mrbt.orbit.budget.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.enums.BudgetPeriodType;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.common.core.model.PageResult;

@ExtendWith(MockitoExtension.class)
class GetBudgetServiceTest {

	@Mock
	private BudgetRepositoryPort budgetRepositoryPort;

	@InjectMocks
	private GetBudgetService getBudgetService;

	@Test
	void getBudgetById_returnsBudget() {
		UUID budgetId = UUID.randomUUID();
		Budget budget = Budget.builder().id(budgetId).userId(UUID.randomUUID()).name("Monthly")
				.periodType(BudgetPeriodType.MONTHLY).startDate(LocalDate.of(2026, 3, 1))
				.endDate(LocalDate.of(2026, 3, 31)).totalAmount(new BigDecimal("500")).status(BudgetStatus.ACTIVE)
				.items(List.of()).build();

		when(budgetRepositoryPort.findById(budgetId)).thenReturn(Optional.of(budget));

		Optional<Budget> result = getBudgetService.getBudgetById(budgetId);

		assertThat(result).isPresent();
		assertThat(result.get().getId()).isEqualTo(budgetId);
		assertThat(result.get().getName()).isEqualTo("Monthly");
	}

	@Test
	void getBudgetsByUserId_returnsPageResult() {
		UUID userId = UUID.randomUUID();
		Budget budget = Budget.builder().id(UUID.randomUUID()).userId(userId).name("Monthly")
				.periodType(BudgetPeriodType.MONTHLY).status(BudgetStatus.ACTIVE).build();
		PageResult<Budget> page = new PageResult<>(List.of(budget), 1L, 1, 0, 20);

		when(budgetRepositoryPort.findByUserId(userId, 0, 20)).thenReturn(page);

		PageResult<Budget> result = getBudgetService.getBudgetsByUserId(userId, 0, 20);

		assertThat(result.content()).hasSize(1);
		assertThat(result.totalElements()).isEqualTo(1L);
		assertThat(result.page()).isZero();
	}

	@Test
	void archiveBudget_setsStatusToArchived() {
		UUID budgetId = UUID.randomUUID();
		Budget budget = Budget.builder().id(budgetId).userId(UUID.randomUUID()).name("Monthly")
				.status(BudgetStatus.ACTIVE).build();

		when(budgetRepositoryPort.findById(budgetId)).thenReturn(Optional.of(budget));
		when(budgetRepositoryPort.save(any(Budget.class))).thenAnswer(inv -> inv.getArgument(0));

		getBudgetService.archiveBudget(budgetId);

		assertThat(budget.getStatus()).isEqualTo(BudgetStatus.ARCHIVED);
		verify(budgetRepositoryPort).save(budget);
	}

}
