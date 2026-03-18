package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.common.core.port.in.UseCase;

public interface CreateBudgetUseCase extends UseCase {
	Budget createBudget(Budget budget);
}
