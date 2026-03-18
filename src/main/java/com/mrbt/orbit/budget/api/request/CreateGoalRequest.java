package com.mrbt.orbit.budget.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
public record CreateGoalRequest(@NotNull(message = "User ID is required") UUID userId,
		@NotBlank(message = "Goal name is required") String name,
		@NotNull(message = "Target amount is required") @Positive BigDecimal targetAmount, LocalDate targetDate,
		UUID linkedAccountId) {
}
