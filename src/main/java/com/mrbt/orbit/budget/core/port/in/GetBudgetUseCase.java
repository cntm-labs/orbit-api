package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.Optional;
import java.util.UUID;

public interface GetBudgetUseCase extends UseCase {
	Optional<Budget> getBudgetById(UUID budgetId);
	PageResult<Budget> getBudgetsByUserId(UUID userId, int page, int size);
}
