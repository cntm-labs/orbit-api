package com.mrbt.orbit.budget.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateGoalServiceTest {

	@Mock
	private GoalRepositoryPort goalRepositoryPort;

	@InjectMocks
	private CreateGoalService createGoalService;

	@Test
	void createGoal_defaultsStatusToInProgress() {
		Goal goal = Goal.builder().userId(UUID.randomUUID()).name("Vacation").targetAmount(new BigDecimal("5000"))
				.targetDate(LocalDate.of(2026, 12, 31)).build();

		when(goalRepositoryPort.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

		Goal result = createGoalService.createGoal(goal);

		assertThat(result.getStatus()).isEqualTo(GoalStatus.IN_PROGRESS);
	}

	@Test
	void createGoal_defaultsCurrentAmountToZero() {
		Goal goal = Goal.builder().userId(UUID.randomUUID()).name("Emergency Fund")
				.targetAmount(new BigDecimal("10000")).build();

		when(goalRepositoryPort.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

		Goal result = createGoalService.createGoal(goal);

		assertThat(result.getCurrentAmount()).isEqualByComparingTo(BigDecimal.ZERO);
	}

}
