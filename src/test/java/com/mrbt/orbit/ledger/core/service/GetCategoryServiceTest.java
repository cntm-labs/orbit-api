package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
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
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetCategoryServiceTest {

	@Mock
	private CategoryRepositoryPort categoryRepositoryPort;

	@InjectMocks
	private GetCategoryService getCategoryService;

	@Test
	void getCategoryById_returnsCategory() {
		UUID catId = UUID.randomUUID();
		Category category = Category.builder().id(catId).name("Food").type(CategoryType.EXPENSE).build();

		when(categoryRepositoryPort.findById(catId)).thenReturn(Optional.of(category));

		Optional<Category> result = getCategoryService.getCategoryById(catId);

		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo("Food");
	}

	@Test
	void getCategoryById_returnsEmptyWhenNotFound() {
		UUID catId = UUID.randomUUID();
		when(categoryRepositoryPort.findById(catId)).thenReturn(Optional.empty());

		assertThat(getCategoryService.getCategoryById(catId)).isEmpty();
	}

	@Test
	void getSystemCategories_returnsList() {
		List<Category> categories = List.of(Category.builder().name("Food").isSystem(true).build(),
				Category.builder().name("Transport").isSystem(true).build());

		when(categoryRepositoryPort.findSystemCategories()).thenReturn(categories);

		List<Category> result = getCategoryService.getSystemCategories();

		assertThat(result).hasSize(2);
		verify(categoryRepositoryPort).findSystemCategories();
	}

	@Test
	void getUserCategories_returnsList() {
		UUID userId = UUID.randomUUID();
		List<Category> categories = List.of(Category.builder().name("Custom").build());

		when(categoryRepositoryPort.findByUserId(userId)).thenReturn(categories);

		List<Category> result = getCategoryService.getUserCategories(userId);

		assertThat(result).hasSize(1);
		verify(categoryRepositoryPort).findByUserId(userId);
	}

}
