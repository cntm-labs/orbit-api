package com.mrbt.orbit.budget.api.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record GoalResponse(UUID id, UUID userId, String name, BigDecimal targetAmount, BigDecimal currentAmount,
		LocalDate targetDate, UUID linkedAccountId, String status, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
