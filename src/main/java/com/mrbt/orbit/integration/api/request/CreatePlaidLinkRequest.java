package com.mrbt.orbit.integration.api.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePlaidLinkRequest(@NotNull UUID userId, @NotBlank String publicToken) {
}
