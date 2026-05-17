package com.mrbt.orbit.audit.core.model;

import java.util.UUID;

import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.common.core.model.BaseDomainModel;

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
public class AuditLog extends BaseDomainModel {
	private UUID userId;
	private AuditAction action;
	private String entityType;
	private UUID entityId;
	private String changesJson;
	private String ipAddress;
}
