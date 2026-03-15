package com.mrbt.orbit.integration.infrastructure.entity;

import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;
import com.mrbt.orbit.integration.core.model.enums.PlaidLinkStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plaid_links")
@Getter
@Setter
@NoArgsConstructor
public class PlaidLinkEntity extends BaseEntity {

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false, unique = true)
	private String plaidItemId;

	@Column(nullable = false)
	private String accessTokenEncrypted;

	private String institutionName;

	private String syncCursor;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PlaidLinkStatus status;

	private String errorCode;

}
