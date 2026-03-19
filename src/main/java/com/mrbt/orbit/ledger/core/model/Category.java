package com.mrbt.orbit.ledger.core.model;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import com.mrbt.orbit.ledger.core.model.enums.CategoryStatus;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Category extends BaseDomainModel {
	private UUID userId;
	private String name;
	private CategoryType type;
	private String icon;
	private String color;
	private Boolean isSystem;
	private CategoryStatus status;
	private UUID parentCategoryId;
}
