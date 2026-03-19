package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryStatus;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryServiceTest {

	@Mock
	private CategoryRepositoryPort categoryRepositoryPort;

	@InjectMocks
	private UpdateCategoryService updateCategoryService;

	@Test
	void updateCategory_updatesName() {
		UUID categoryId = UUID.randomUUID();
		Category category = Category.builder().id(categoryId).name("Old Name").type(CategoryType.EXPENSE)
				.status(CategoryStatus.ACTIVE).build();

		when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.of(category));
		when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

		Category result = updateCategoryService.updateCategory(categoryId, "New Name", null);

		assertThat(result.getName()).isEqualTo("New Name");
		verify(categoryRepositoryPort).save(category);
	}

	@Test
	void updateCategory_throwsWhenNotFound() {
		UUID categoryId = UUID.randomUUID();
		when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateCategoryService.updateCategory(categoryId, "Name", null))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void deleteCategory_setsStatusToInactive() {
		UUID categoryId = UUID.randomUUID();
		Category category = Category.builder().id(categoryId).name("Food").type(CategoryType.EXPENSE)
				.status(CategoryStatus.ACTIVE).build();

		when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.of(category));
		when(categoryRepositoryPort.save(any(Category.class))).thenAnswer(inv -> inv.getArgument(0));

		updateCategoryService.deleteCategory(categoryId);

		assertThat(category.getStatus()).isEqualTo(CategoryStatus.INACTIVE);
		verify(categoryRepositoryPort).save(category);
	}

	@Test
	void deleteCategory_throwsWhenNotFound() {
		UUID categoryId = UUID.randomUUID();
		when(categoryRepositoryPort.findById(categoryId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateCategoryService.deleteCategory(categoryId))
				.isInstanceOf(ResourceNotFoundException.class);
	}

}
