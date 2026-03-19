package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.UUID;

public interface UpdateBudgetUseCase extends UseCase {

	Budget updateBudget(UUID budgetId, String name);
}
