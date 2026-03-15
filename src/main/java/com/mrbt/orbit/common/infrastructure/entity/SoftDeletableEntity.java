package com.mrbt.orbit.common.infrastructure.entity;

import java.time.Instant;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SoftDeletableEntity extends BaseEntity {

	private Instant deletedAt;

	public void softDelete() {
		this.deletedAt = Instant.now();
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}

}
