package com.mrbt.orbit.budget.core.port.out;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.common.core.model.PageResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepositoryPort {
	Budget save(Budget budget);
	Optional<Budget> findById(UUID id);
	PageResult<Budget> findByUserId(UUID userId, int page, int size);
	List<BudgetItem> findActiveItemsByCategoryIdAndDate(UUID categoryId, LocalDate date);
	void updateSpentAmount(UUID budgetItemId, BigDecimal amount);
}
