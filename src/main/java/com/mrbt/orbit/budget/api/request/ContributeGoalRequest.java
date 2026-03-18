package com.mrbt.orbit.budget.api.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ContributeGoalRequest(@NotNull(message = "Amount is required") @Positive BigDecimal amount) {
}
