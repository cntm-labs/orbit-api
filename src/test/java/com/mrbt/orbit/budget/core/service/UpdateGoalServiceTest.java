package com.mrbt.orbit.budget.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class UpdateGoalServiceTest {

	@Mock
	private GoalRepositoryPort goalRepositoryPort;

	@InjectMocks
	private UpdateGoalService updateGoalService;

	@Test
	void updateGoal_updatesName() {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).name("Old Goal").targetAmount(new BigDecimal("5000"))
				.currentAmount(BigDecimal.ZERO).status(GoalStatus.IN_PROGRESS).build();

		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.of(goal));
		when(goalRepositoryPort.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

		Goal result = updateGoalService.updateGoal(goalId, "New Goal", null, null);

		assertThat(result.getName()).isEqualTo("New Goal");
		verify(goalRepositoryPort).save(goal);
	}

	@Test
	void updateGoal_throwsWhenNotFound() {
		UUID goalId = UUID.randomUUID();
		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateGoalService.updateGoal(goalId, "Name", null, null))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void cancelGoal_setsStatusToCancelled() {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).name("Vacation").targetAmount(new BigDecimal("5000"))
				.currentAmount(BigDecimal.ZERO).status(GoalStatus.IN_PROGRESS).build();

		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.of(goal));
		when(goalRepositoryPort.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

		updateGoalService.cancelGoal(goalId);

		assertThat(goal.getStatus()).isEqualTo(GoalStatus.CANCELLED);
		verify(goalRepositoryPort).save(goal);
	}

	@Test
	void cancelGoal_throwsWhenNotFound() {
		UUID goalId = UUID.randomUUID();
		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateGoalService.cancelGoal(goalId)).isInstanceOf(ResourceNotFoundException.class);
	}

}
