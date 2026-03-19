package com.mrbt.orbit.ledger.api.request;

import lombok.Builder;

@Builder
public record UpdateAccountRequest(String name) {
}
