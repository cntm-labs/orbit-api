package com.mrbt.orbit.integration.api.response;

import java.util.UUID;

import com.mrbt.orbit.integration.core.model.enums.PlaidLinkStatus;

public record PlaidLinkResponse(UUID id, UUID userId, String institutionName, PlaidLinkStatus status,
		String errorCode) {
}
