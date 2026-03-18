package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.UUID;

public interface ArchiveBudgetUseCase extends UseCase {
	void archiveBudget(UUID budgetId);
}
