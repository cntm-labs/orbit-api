package com.mrbt.orbit.ledger.infrastructure.mapper;

import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class CategoryMapper {

	public Category toDomain(CategoryEntity entity) {
		if (entity == null)
			return null;

		return Category.builder().id(entity.getId()).userId(entity.getUserId()).name(entity.getName())
				.type(entity.getType()).icon(entity.getIcon()).color(entity.getColor()).status(entity.getStatus())
				.isSystem(entity.getIsSystem())
				.parentCategoryId(entity.getParentCategory() != null ? entity.getParentCategory().getId() : null)
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.build();
	}

	public CategoryEntity toEntity(Category domain) {
		if (domain == null)
			return null;

		CategoryEntity entity = new CategoryEntity();
		entity.setId(domain.getId());
		entity.setUserId(domain.getUserId());
		entity.setName(domain.getName());
		entity.setType(domain.getType());
		entity.setIcon(domain.getIcon());
		entity.setColor(domain.getColor());
		entity.setStatus(domain.getStatus());
		entity.setIsSystem(domain.getIsSystem());

		// Note: The parentCategory relation must be resolved by the RepositoryAdapter
		// so we don't handle it fully in this simple mapper unless we pass the
		// EntityManager.

		return entity;
	}

}
