package com.mrbt.orbit.ledger.api.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateTransactionRequest(String description, UUID categoryId, Boolean isReviewed) {
}
