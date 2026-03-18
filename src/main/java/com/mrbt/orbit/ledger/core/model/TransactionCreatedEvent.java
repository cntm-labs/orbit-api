package com.mrbt.orbit.ledger.core.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionCreatedEvent(UUID categoryId, BigDecimal amount, Instant transactionDate) {
}
