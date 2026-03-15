package com.mrbt.orbit.ledger.infrastructure.entity;

import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.CreatedOnlyEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class TagEntity extends CreatedOnlyEntity {

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private String name;

	private String color;

}
