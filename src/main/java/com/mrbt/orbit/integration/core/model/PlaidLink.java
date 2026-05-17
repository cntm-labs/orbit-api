package com.mrbt.orbit.integration.core.model;

import java.util.UUID;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import com.mrbt.orbit.integration.core.model.enums.PlaidLinkStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PlaidLink extends BaseDomainModel {
	private UUID userId;
	private String plaidItemId;
	private String accessTokenEncrypted;
	private String institutionName;
	private String syncCursor;
	private PlaidLinkStatus status;
	private String errorCode;
}
