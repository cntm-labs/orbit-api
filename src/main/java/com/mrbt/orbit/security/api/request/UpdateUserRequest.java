package com.mrbt.orbit.security.api.request;

import lombok.Builder;

@Builder
public record UpdateUserRequest(String firstName, String lastName, String baseCurrency, String timezone) {
}
