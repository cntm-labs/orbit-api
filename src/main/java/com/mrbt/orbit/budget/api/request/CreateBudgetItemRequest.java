package com.mrbt.orbit.budget.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreateBudgetItemRequest(@NotNull(message = "Category ID is required") UUID categoryId,
		@NotNull(message = "Allocated amount is required") @Positive BigDecimal allocatedAmount,
		Integer alertThresholdPct) {
}
