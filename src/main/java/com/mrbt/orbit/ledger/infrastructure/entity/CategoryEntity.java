package com.mrbt.orbit.ledger.infrastructure.entity;

import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;
import com.mrbt.orbit.ledger.core.model.enums.CategoryStatus;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity extends BaseEntity {

	private UUID userId;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoryType type;

	private String icon;

	private String color;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CategoryStatus status;

	@Column(nullable = false)
	private Boolean isSystem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_category_id")
	private CategoryEntity parentCategory;

	@OneToMany(mappedBy = "parentCategory")
	private List<CategoryEntity> subCategories;

}
