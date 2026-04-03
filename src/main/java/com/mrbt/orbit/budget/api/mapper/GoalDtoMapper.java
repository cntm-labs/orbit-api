package com.mrbt.orbit.budget.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.budget.api.request.CreateGoalRequest;
import com.mrbt.orbit.budget.api.response.GoalResponse;
import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;

@Component
public class GoalDtoMapper extends GenericDtoMapper<CreateGoalRequest, GoalResponse, Goal> {

	@Override
	public Goal toDomain(CreateGoalRequest request) {
		if (request == null) {
			return null;
		}
		return Goal.builder().userId(request.userId()).name(request.name()).targetAmount(request.targetAmount())
				.targetDate(request.targetDate()).linkedAccountId(request.linkedAccountId()).build();
	}

	@Override
	public GoalResponse toResponse(Goal domain) {
		if (domain == null) {
			return null;
		}
		return GoalResponse.builder().id(domain.getId()).userId(domain.getUserId()).name(domain.getName())
				.targetAmount(domain.getTargetAmount()).currentAmount(domain.getCurrentAmount())
				.targetDate(domain.getTargetDate()).linkedAccountId(domain.getLinkedAccountId())
				.status(EnumUtils.toStringOrNull(domain.getStatus())).createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt()).build();
	}

}
