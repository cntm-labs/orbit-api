package com.mrbt.orbit.budget.infrastructure.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.budget.infrastructure.entity.BudgetEntity;
import com.mrbt.orbit.budget.infrastructure.entity.BudgetItemEntity;
import com.mrbt.orbit.budget.infrastructure.mapper.BudgetEntityMapper;
import com.mrbt.orbit.common.core.model.PageResult;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BudgetRepositoryAdapter implements BudgetRepositoryPort {

	private final BudgetRepository budgetRepository;

	private final BudgetItemRepository budgetItemRepository;

	private final BudgetEntityMapper mapper;

	@Override
	public Budget save(Budget budget) {
		BudgetEntity entity = mapper.toEntity(budget);
		BudgetEntity saved = budgetRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Budget> findById(UUID id) {
		return budgetRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public PageResult<Budget> findByUserId(UUID userId, int page, int size) {
		Page<BudgetEntity> entityPage = budgetRepository.findByUserIdOrderByCreatedAtDesc(userId,
				PageRequest.of(page, size));
		return new PageResult<>(mapper.toDomainList(entityPage.getContent()), entityPage.getTotalElements(),
				entityPage.getTotalPages(), entityPage.getNumber(), entityPage.getSize());
	}

	@Override
	public List<BudgetItem> findActiveItemsByCategoryIdAndDate(UUID categoryId, LocalDate date) {
		List<BudgetItemEntity> entities = budgetItemRepository.findActiveByCategoryIdAndDate(categoryId, date);
		return entities.stream().map(mapper::itemToDomain).toList();
	}

	@Override
	@Transactional
	public void updateSpentAmount(UUID budgetItemId, BigDecimal amount) {
		budgetItemRepository.updateSpentAmountAtomically(budgetItemId, amount);
	}

}
