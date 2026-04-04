package com.mrbt.orbit.ledger.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.ledger.api.request.CreateCategoryRequest;
import com.mrbt.orbit.ledger.api.response.CategoryResponse;
import com.mrbt.orbit.ledger.core.model.Category;

@Component
public class CategoryDtoMapper extends GenericDtoMapper<CreateCategoryRequest, CategoryResponse, Category> {

	@Override
	public Category toDomain(CreateCategoryRequest request) {
		if (request == null) {
			return null;
		}
		return Category.builder().userId(request.userId()).name(request.name()).type(request.type())
				.icon(request.icon()).color(request.color()).parentCategoryId(request.parentCategoryId()).build();
	}

	@Override
	public CategoryResponse toResponse(Category domain) {
		if (domain == null) {
			return null;
		}
		return CategoryResponse.builder().id(domain.getId()).userId(domain.getUserId()).name(domain.getName())
				.type(EnumUtils.toStringOrNull(domain.getType())).icon(domain.getIcon()).color(domain.getColor())
				.status(EnumUtils.toStringOrNull(domain.getStatus())).isSystem(domain.getIsSystem())
				.parentCategoryId(domain.getParentCategoryId()).createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt()).build();
	}

}
