package com.mrbt.orbit.budget.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import com.mrbt.orbit.common.exception.BadRequestException;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ContributeGoalServiceTest {

	@Mock
	private GoalRepositoryPort goalRepositoryPort;

	@InjectMocks
	private ContributeGoalService contributeGoalService;

	@Test
	void contribute_addsAmountToCurrentAmount() {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).userId(UUID.randomUUID()).name("Vacation")
				.targetAmount(new BigDecimal("1000")).currentAmount(new BigDecimal("50")).status(GoalStatus.IN_PROGRESS)
				.build();

		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.of(goal));
		when(goalRepositoryPort.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

		Goal result = contributeGoalService.contribute(goalId, new BigDecimal("30"));

		assertThat(result.getCurrentAmount()).isEqualByComparingTo(new BigDecimal("80"));
	}

	@Test
	void contribute_transitionsToAchievedWhenTargetReached() {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).userId(UUID.randomUUID()).name("Car").targetAmount(new BigDecimal("100"))
				.currentAmount(new BigDecimal("80")).status(GoalStatus.IN_PROGRESS).build();

		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.of(goal));
		when(goalRepositoryPort.save(any(Goal.class))).thenAnswer(inv -> inv.getArgument(0));

		Goal result = contributeGoalService.contribute(goalId, new BigDecimal("20"));

		assertThat(result.getStatus()).isEqualTo(GoalStatus.ACHIEVED);
		assertThat(result.getCurrentAmount()).isEqualByComparingTo(new BigDecimal("100"));
	}

	@Test
	void contribute_throwsWhenAmountNotPositive() {
		UUID goalId = UUID.randomUUID();

		assertThatThrownBy(() -> contributeGoalService.contribute(goalId, BigDecimal.ZERO))
				.isInstanceOf(BadRequestException.class).hasMessageContaining("positive");

		verify(goalRepositoryPort, never()).save(any());
	}

	@Test
	void contribute_throwsWhenGoalIsLinkedToAccount() {
		UUID goalId = UUID.randomUUID();
		Goal goal = Goal.builder().id(goalId).userId(UUID.randomUUID()).name("Linked Goal")
				.targetAmount(new BigDecimal("500")).currentAmount(new BigDecimal("100"))
				.linkedAccountId(UUID.randomUUID()).status(GoalStatus.IN_PROGRESS).build();

		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.of(goal));

		assertThatThrownBy(() -> contributeGoalService.contribute(goalId, new BigDecimal("50")))
				.isInstanceOf(BadRequestException.class).hasMessageContaining("linked");

		verify(goalRepositoryPort, never()).save(any());
	}

	@Test
	void contribute_throwsWhenGoalNotFound() {
		UUID goalId = UUID.randomUUID();

		when(goalRepositoryPort.findById(goalId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> contributeGoalService.contribute(goalId, new BigDecimal("10")))
				.isInstanceOf(ResourceNotFoundException.class).hasMessageContaining("Goal");

		verify(goalRepositoryPort, never()).save(any());
	}

}
