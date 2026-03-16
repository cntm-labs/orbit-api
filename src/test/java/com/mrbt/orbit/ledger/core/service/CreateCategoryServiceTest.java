package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.DuplicateResourceException;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateCategoryServiceTest {

	@Mock
	private CategoryRepositoryPort categoryRepositoryPort;

	@InjectMocks
	private CreateCategoryService createCategoryService;

	@Test
	void createCategory_setsIsSystemFalseForUserCategory() {
		UUID userId = UUID.randomUUID();
		Category category = Category.builder().userId(userId).name("Food").type(CategoryType.EXPENSE).build();

		when(categoryRepositoryPort.existsByUserIdAndName(userId, "Food")).thenReturn(false);
		when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

		Category result = createCategoryService.createCategory(category);

		assertThat(result.getIsSystem()).isFalse();
	}

	@Test
	void createCategory_setsIsSystemTrueWhenNoUser() {
		Category category = Category.builder().userId(null).name("Uncategorized").type(CategoryType.EXPENSE).build();

		when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

		Category result = createCategoryService.createCategory(category);

		assertThat(result.getIsSystem()).isTrue();
	}

	@Test
	void createCategory_preservesExplicitIsSystem() {
		UUID userId = UUID.randomUUID();
		Category category = Category.builder().userId(userId).name("Custom").type(CategoryType.INCOME).isSystem(true)
				.build();

		when(categoryRepositoryPort.existsByUserIdAndName(userId, "Custom")).thenReturn(false);
		when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

		Category result = createCategoryService.createCategory(category);

		assertThat(result.getIsSystem()).isTrue();
	}

	@Test
	void createCategory_throwsOnDuplicateNameForUser() {
		UUID userId = UUID.randomUUID();
		Category category = Category.builder().userId(userId).name("Duplicate").type(CategoryType.EXPENSE).build();

		when(categoryRepositoryPort.existsByUserIdAndName(userId, "Duplicate")).thenReturn(true);

		assertThatThrownBy(() -> createCategoryService.createCategory(category))
				.isInstanceOf(DuplicateResourceException.class).hasMessageContaining("Duplicate");

		verify(categoryRepositoryPort, never()).save(any());
	}

	@Test
	void createCategory_skipsUniqueCheckWhenNoUserId() {
		Category category = Category.builder().userId(null).name("System Cat").type(CategoryType.EXPENSE).build();

		when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

		createCategoryService.createCategory(category);

		verify(categoryRepositoryPort, never()).existsByUserIdAndName(any(), any());
		verify(categoryRepositoryPort).save(any());
	}

}
