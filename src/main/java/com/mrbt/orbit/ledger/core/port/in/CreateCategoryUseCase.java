package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Category;

public interface CreateCategoryUseCase extends UseCase {
	Category createCategory(Category category);
}
