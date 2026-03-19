package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Category;

import java.util.UUID;

public interface UpdateCategoryUseCase extends UseCase {

	Category updateCategory(UUID categoryId, String name, UUID parentCategoryId);
}
