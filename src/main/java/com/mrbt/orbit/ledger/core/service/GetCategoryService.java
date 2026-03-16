package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.port.in.GetCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCategoryService implements GetCategoryUseCase {

	private final CategoryRepositoryPort categoryRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<Category> getCategoryById(UUID categoryId) {
		return categoryRepositoryPort.findById(categoryId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> getSystemCategories() {
		return categoryRepositoryPort.findSystemCategories();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> getUserCategories(UUID userId) {
		return categoryRepositoryPort.findByUserId(userId);
	}
}
