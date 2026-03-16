package com.mrbt.orbit.ledger.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.mapper.CategoryMapper;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryAdapterTest {

	@Mock
	private CategoryRepository springDataRepository;

	@Mock
	private CategoryMapper mapper;

	@InjectMocks
	private CategoryRepositoryAdapter adapter;

	@Test
	void save_convertsAndPersists() {
		Category domain = Category.builder().name("Food").type(CategoryType.EXPENSE).build();
		CategoryEntity entity = new CategoryEntity();
		CategoryEntity savedEntity = new CategoryEntity();
		Category expected = Category.builder().id(UUID.randomUUID()).name("Food").build();

		when(mapper.toEntity(domain)).thenReturn(entity);
		when(springDataRepository.save(entity)).thenReturn(savedEntity);
		when(mapper.toDomain(savedEntity)).thenReturn(expected);

		assertThat(adapter.save(domain)).isEqualTo(expected);
	}

	@Test
	void findById_returnsCategory() {
		UUID id = UUID.randomUUID();
		CategoryEntity entity = new CategoryEntity();
		Category expected = Category.builder().id(id).name("Food").build();

		when(springDataRepository.findById(id)).thenReturn(Optional.of(entity));
		when(mapper.toDomain(entity)).thenReturn(expected);

		assertThat(adapter.findById(id)).isPresent().contains(expected);
	}

	@Test
	void findSystemCategories_returnsList() {
		CategoryEntity entity = new CategoryEntity();
		Category mapped = Category.builder().name("System").isSystem(true).build();

		when(springDataRepository.findByIsSystemTrue()).thenReturn(List.of(entity));
		when(mapper.toDomain(entity)).thenReturn(mapped);

		assertThat(adapter.findSystemCategories()).hasSize(1);
	}

	@Test
	void findByUserId_returnsList() {
		UUID userId = UUID.randomUUID();
		CategoryEntity entity = new CategoryEntity();
		Category mapped = Category.builder().name("Custom").build();

		when(springDataRepository.findByUserId(userId)).thenReturn(List.of(entity));
		when(mapper.toDomain(entity)).thenReturn(mapped);

		assertThat(adapter.findByUserId(userId)).hasSize(1);
	}

	@Test
	void existsByUserIdAndName_delegatesToRepo() {
		UUID userId = UUID.randomUUID();
		when(springDataRepository.existsByUserIdAndName(userId, "Food")).thenReturn(true);

		assertThat(adapter.existsByUserIdAndName(userId, "Food")).isTrue();
	}

}
