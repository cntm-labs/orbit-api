package com.mrbt.orbit.ledger.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.api.request.CreateCategoryRequest;
import com.mrbt.orbit.ledger.api.response.CategoryResponse;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryStatus;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;

class CategoryDtoMapperTest {

	private final CategoryDtoMapper mapper = new CategoryDtoMapper();

	@Test
	void toDomain_mapsAllFields() {
		UUID userId = UUID.randomUUID();
		UUID parentId = UUID.randomUUID();

		CreateCategoryRequest request = CreateCategoryRequest.builder().userId(userId).name("Food")
				.type(CategoryType.EXPENSE).icon("utensils").color("#FF0000").parentCategoryId(parentId).build();

		Category result = mapper.toDomain(request);

		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getName()).isEqualTo("Food");
		assertThat(result.getType()).isEqualTo(CategoryType.EXPENSE);
		assertThat(result.getIcon()).isEqualTo("utensils");
		assertThat(result.getColor()).isEqualTo("#FF0000");
		assertThat(result.getParentCategoryId()).isEqualTo(parentId);
	}

	@Test
	void toDomain_returnsNullForNull() {
		assertThat(mapper.toDomain(null)).isNull();
	}

	@Test
	void toResponse_mapsAllFields() {
		UUID id = UUID.randomUUID();
		OffsetDateTime now = OffsetDateTime.now();

		Category domain = Category.builder().id(id).userId(UUID.randomUUID()).name("Transport")
				.type(CategoryType.EXPENSE).icon("car").color("#0000FF").status(CategoryStatus.ACTIVE).isSystem(false)
				.parentCategoryId(UUID.randomUUID()).createdAt(now).updatedAt(now).build();

		CategoryResponse result = mapper.toResponse(domain);

		assertThat(result.id()).isEqualTo(id);
		assertThat(result.type()).isEqualTo("EXPENSE");
		assertThat(result.status()).isEqualTo("ACTIVE");
		assertThat(result.isSystem()).isFalse();
		assertThat(result.createdAt()).isEqualTo(now);
	}

	@Test
	void toResponse_returnsNullForNull() {
		assertThat(mapper.toResponse(null)).isNull();
	}

	@Test
	void toResponse_handlesNullEnums() {
		Category domain = Category.builder().name("Test").build();
		CategoryResponse result = mapper.toResponse(domain);
		assertThat(result.type()).isNull();
		assertThat(result.status()).isNull();
	}

	@Test
	void toResponseList_convertsList() {
		Category c1 = Category.builder().name("C1").build();
		Category c2 = Category.builder().name("C2").build();
		List<CategoryResponse> result = mapper.toResponseList(List.of(c1, c2));
		assertThat(result).hasSize(2);
	}

	@Test
	void toResponseList_returnsEmptyListForNull() {
		assertThat(mapper.toResponseList(null)).isEmpty();
	}

}
