package com.mrbt.orbit.ledger.api.request;

import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateCategoryRequest(UUID userId,

		@NotBlank(message = "Category name cannot be blank") String name,

		@NotNull(message = "Category type is required") CategoryType type,

		String icon,

		String color,

		UUID parentCategoryId) {
}
