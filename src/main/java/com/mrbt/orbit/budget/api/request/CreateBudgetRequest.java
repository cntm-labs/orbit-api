package com.mrbt.orbit.budget.api.request;

import com.mrbt.orbit.budget.core.model.enums.BudgetPeriodType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
public record CreateBudgetRequest(@NotNull(message = "User ID is required") UUID userId,
		@NotBlank(message = "Budget name is required") String name,
		@NotNull(message = "Period type is required") BudgetPeriodType periodType,
		@NotNull(message = "Start date is required") LocalDate startDate,
		@NotNull(message = "End date is required") LocalDate endDate,
		@NotNull(message = "Budget items are required") @Valid List<CreateBudgetItemRequest> items) {
}
