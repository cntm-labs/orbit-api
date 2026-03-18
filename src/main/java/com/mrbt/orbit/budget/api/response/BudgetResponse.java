package com.mrbt.orbit.budget.api.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record BudgetResponse(UUID id, UUID userId, String name, String periodType, LocalDate startDate,
		LocalDate endDate, BigDecimal totalAmount, String status, List<BudgetItemResponse> items,
		OffsetDateTime createdAt, OffsetDateTime updatedAt) {
}
