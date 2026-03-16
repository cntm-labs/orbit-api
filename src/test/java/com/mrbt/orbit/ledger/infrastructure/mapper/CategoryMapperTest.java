package com.mrbt.orbit.ledger.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.api.response.CategoryResponse;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;

class CategoryMapperTest {

	private final CategoryMapper mapper = new CategoryMapper();

	@Test
	void toDomain_mapsAllFields() {
		CategoryEntity entity = new CategoryEntity();
		UUID id = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		entity.setId(id);
		entity.setUserId(userId);
		entity.setName("Food");
		entity.setType(CategoryType.EXPENSE);
		entity.setIcon("utensils");
		entity.setColor("#FF5733");
		entity.setIsSystem(false);
		entity.setCreatedAt(Instant.parse("2026-01-01T00:00:00Z"));

		Category result = mapper.toDomain(entity);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getName()).isEqualTo("Food");
		assertThat(result.getType()).isEqualTo(CategoryType.EXPENSE);
		assertThat(result.getIcon()).isEqualTo("utensils");
		assertThat(result.getColor()).isEqualTo("#FF5733");
		assertThat(result.getIsSystem()).isFalse();
		assertThat(result.getParentCategoryId()).isNull();
	}

	@Test
	void toDomain_mapsParentCategoryId() {
		CategoryEntity parent = new CategoryEntity();
		UUID parentId = UUID.randomUUID();
		parent.setId(parentId);

		CategoryEntity entity = new CategoryEntity();
		entity.setId(UUID.randomUUID());
		entity.setName("Sub Food");
		entity.setType(CategoryType.EXPENSE);
		entity.setParentCategory(parent);

		Category result = mapper.toDomain(entity);

		assertThat(result.getParentCategoryId()).isEqualTo(parentId);
	}

	@Test
	void toDomain_returnsNullForNull() {
		assertThat(mapper.toDomain((CategoryEntity) null)).isNull();
	}

	@Test
	void toEntity_mapsAllFields() {
		UUID id = UUID.randomUUID();
		Category domain = Category.builder().id(id).userId(UUID.randomUUID()).name("Transport")
				.type(CategoryType.EXPENSE).icon("car").color("#00FF00").isSystem(false).build();

		CategoryEntity result = mapper.toEntity(domain);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getName()).isEqualTo("Transport");
		assertThat(result.getType()).isEqualTo(CategoryType.EXPENSE);
		assertThat(result.getIcon()).isEqualTo("car");
		assertThat(result.getColor()).isEqualTo("#00FF00");
		assertThat(result.getIsSystem()).isFalse();
	}

	@Test
	void toEntity_returnsNullForNull() {
		assertThat(mapper.toEntity(null)).isNull();
	}

	@Test
	void toResponse_mapsAllFields() {
		UUID id = UUID.randomUUID();
		UUID parentId = UUID.randomUUID();
		Category domain = Category.builder().id(id).userId(UUID.randomUUID()).name("Food").type(CategoryType.EXPENSE)
				.icon("utensils").color("#FF5733").isSystem(false).parentCategoryId(parentId).build();

		CategoryResponse result = mapper.toResponse(domain);

		assertThat(result.id()).isEqualTo(id);
		assertThat(result.name()).isEqualTo("Food");
		assertThat(result.type()).isEqualTo("EXPENSE");
		assertThat(result.parentCategoryId()).isEqualTo(parentId);
	}

	@Test
	void toResponse_returnsNullForNull() {
		assertThat(mapper.toResponse(null)).isNull();
	}

	@Test
	void toResponse_handlesNullType() {
		Category domain = Category.builder().name("Minimal").build();

		CategoryResponse result = mapper.toResponse(domain);

		assertThat(result.type()).isNull();
	}

}
