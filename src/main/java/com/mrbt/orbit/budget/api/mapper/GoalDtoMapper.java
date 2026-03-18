package com.mrbt.orbit.budget.api.mapper;

import com.mrbt.orbit.budget.api.request.CreateGoalRequest;
import com.mrbt.orbit.budget.api.response.GoalResponse;
import com.mrbt.orbit.budget.core.model.Goal;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoalDtoMapper {

	public Goal toDomain(CreateGoalRequest request) {
		if (request == null)
			return null;
		return Goal.builder().userId(request.userId()).name(request.name()).targetAmount(request.targetAmount())
				.targetDate(request.targetDate()).linkedAccountId(request.linkedAccountId()).build();
	}

	public GoalResponse toResponse(Goal domain) {
		if (domain == null)
			return null;
		return GoalResponse.builder().id(domain.getId()).userId(domain.getUserId()).name(domain.getName())
				.targetAmount(domain.getTargetAmount()).currentAmount(domain.getCurrentAmount())
				.targetDate(domain.getTargetDate()).linkedAccountId(domain.getLinkedAccountId())
				.status(domain.getStatus() != null ? domain.getStatus().name() : null).createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt()).build();
	}

	public List<GoalResponse> toResponseList(List<Goal> domains) {
		if (domains == null)
			return null;
		return domains.stream().map(this::toResponse).toList();
	}
}
