package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.common.core.port.in.UseCase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface UpdateGoalUseCase extends UseCase {

	Goal updateGoal(UUID goalId, String name, BigDecimal targetAmount, LocalDate targetDate);
}
