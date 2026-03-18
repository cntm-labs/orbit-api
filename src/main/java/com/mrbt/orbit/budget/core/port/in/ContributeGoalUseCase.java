package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.common.core.port.in.UseCase;

import java.math.BigDecimal;
import java.util.UUID;

public interface ContributeGoalUseCase extends UseCase {
	Goal contribute(UUID goalId, BigDecimal amount);
}
