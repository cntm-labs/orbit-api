package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryStatus;
import com.mrbt.orbit.ledger.core.port.in.DeleteCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase, DeleteCategoryUseCase {

	private final CategoryRepositoryPort categoryRepositoryPort;

	@Override
	@Transactional
	public Category updateCategory(UUID categoryId, String name, UUID parentCategoryId) {
		Category category = categoryRepositoryPort.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		if (name != null) {
			category.setName(name);
		}
		if (parentCategoryId != null) {
			category.setParentCategoryId(parentCategoryId);
		}
		return categoryRepositoryPort.save(category);
	}

	@Override
	@Transactional
	public void deleteCategory(UUID categoryId) {
		Category category = categoryRepositoryPort.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		category.setStatus(CategoryStatus.INACTIVE);
		categoryRepositoryPort.save(category);
	}
}
