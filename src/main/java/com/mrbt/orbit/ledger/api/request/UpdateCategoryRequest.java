package com.mrbt.orbit.ledger.api.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateCategoryRequest(String name, UUID parentCategoryId) {
}
