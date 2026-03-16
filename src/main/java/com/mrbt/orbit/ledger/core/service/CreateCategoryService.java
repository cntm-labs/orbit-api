package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.common.exception.DuplicateResourceException;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.port.in.CreateCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {

	private final CategoryRepositoryPort categoryRepositoryPort;

	@Override
	@Transactional
	public Category createCategory(Category category) {
		if (category.getUserId() != null
				&& categoryRepositoryPort.existsByUserIdAndName(category.getUserId(), category.getName())) {
			throw new DuplicateResourceException("Category", "Name", category.getName());
		}

		if (category.getIsSystem() == null) {
			category.setIsSystem(category.getUserId() == null);
		}

		return categoryRepositoryPort.save(category);
	}
}
