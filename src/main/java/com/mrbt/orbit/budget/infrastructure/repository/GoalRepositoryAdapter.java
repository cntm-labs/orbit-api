package com.mrbt.orbit.budget.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;
import com.mrbt.orbit.budget.infrastructure.entity.GoalEntity;
import com.mrbt.orbit.budget.infrastructure.mapper.GoalEntityMapper;
import com.mrbt.orbit.common.core.model.PageResult;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class GoalRepositoryAdapter implements GoalRepositoryPort {

	private final GoalRepository goalRepository;

	private final GoalEntityMapper mapper;

	@Override
	public Goal save(Goal goal) {
		GoalEntity entity = mapper.toEntity(goal);
		GoalEntity saved = goalRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Goal> findById(UUID id) {
		return goalRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public PageResult<Goal> findByUserId(UUID userId, int page, int size) {
		Page<GoalEntity> entityPage = goalRepository.findByUserIdOrderByCreatedAtDesc(userId,
				PageRequest.of(page, size));
		return new PageResult<>(mapper.toDomainList(entityPage.getContent()), entityPage.getTotalElements(),
				entityPage.getTotalPages(), entityPage.getNumber(), entityPage.getSize());
	}

}
