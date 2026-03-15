package com.mrbt.orbit.audit.infrastructure.entity;

import java.util.UUID;

import com.mrbt.orbit.audit.core.model.enums.AuditAction;
import com.mrbt.orbit.common.infrastructure.entity.CreatedOnlyEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
public class AuditLogEntity extends CreatedOnlyEntity {

	@Column(nullable = false)
	private UUID userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AuditAction action;

	@Column(nullable = false)
	private String entityType;

	@Column(nullable = false)
	private UUID entityId;

	@Column(columnDefinition = "text")
	private String changesJson;

	private String ipAddress;

}
