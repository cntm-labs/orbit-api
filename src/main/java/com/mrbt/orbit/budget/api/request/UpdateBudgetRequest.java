package com.mrbt.orbit.budget.api.request;

import lombok.Builder;

@Builder
public record UpdateBudgetRequest(String name) {
}
