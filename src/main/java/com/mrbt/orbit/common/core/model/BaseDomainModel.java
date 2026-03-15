package com.mrbt.orbit.common.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.UUID;
import java.time.OffsetDateTime;

/**
 * Base class for all Domain Models in the Core layer. This is isolated from
 * persistence concerns (no JPA).
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDomainModel {
	private UUID id;
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
}
