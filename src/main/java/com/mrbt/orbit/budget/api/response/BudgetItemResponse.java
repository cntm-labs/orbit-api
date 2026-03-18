package com.mrbt.orbit.budget.api.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record BudgetItemResponse(UUID id, UUID categoryId, BigDecimal allocatedAmount, BigDecimal spentAmount,
		Integer alertThresholdPct) {
}
