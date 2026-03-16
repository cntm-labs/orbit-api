package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetCategoryUseCase extends UseCase {
	Optional<Category> getCategoryById(UUID categoryId);
	List<Category> getSystemCategories();
	List<Category> getUserCategories(UUID userId);
}
