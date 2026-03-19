package com.mrbt.orbit.budget.api.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record UpdateGoalRequest(String name, BigDecimal targetAmount, LocalDate targetDate) {
}
