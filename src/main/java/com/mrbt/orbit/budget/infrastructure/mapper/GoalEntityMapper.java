package com.mrbt.orbit.budget.infrastructure.mapper;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.infrastructure.entity.GoalEntity;

@Component
public class GoalEntityMapper {

	public Goal toDomain(GoalEntity entity) {
		if (entity == null)
			return null;

		return Goal.builder().id(entity.getId()).userId(entity.getUserId()).name(entity.getName())
				.targetAmount(entity.getTargetAmount()).currentAmount(entity.getCurrentAmount())
				.targetDate(entity.getTargetDate()).linkedAccountId(entity.getLinkedAccountId())
				.status(entity.getStatus())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.build();
	}

	public GoalEntity toEntity(Goal domain) {
		if (domain == null)
			return null;

		GoalEntity entity = new GoalEntity();
		entity.setId(domain.getId());
		entity.setUserId(domain.getUserId());
		entity.setName(domain.getName());
		entity.setTargetAmount(domain.getTargetAmount());
		entity.setCurrentAmount(domain.getCurrentAmount() != null ? domain.getCurrentAmount() : BigDecimal.ZERO);
		entity.setTargetDate(domain.getTargetDate());
		entity.setLinkedAccountId(domain.getLinkedAccountId());
		entity.setStatus(domain.getStatus());
		return entity;
	}

	public List<Goal> toDomainList(List<GoalEntity> entities) {
		if (entities == null)
			return null;
		return entities.stream().map(this::toDomain).toList();
	}

}
