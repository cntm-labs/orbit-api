package com.mrbt.orbit.ledger.core.port.out;

import com.mrbt.orbit.ledger.core.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepositoryPort {
	Category save(Category category);
	Optional<Category> findById(UUID id);
	List<Category> findSystemCategories();
	List<Category> findByUserId(UUID userId);
	boolean existsByUserIdAndName(UUID userId, String name);
}
