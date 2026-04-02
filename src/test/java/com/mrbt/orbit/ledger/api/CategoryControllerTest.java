package com.mrbt.orbit.ledger.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.orbit.ledger.api.request.CreateCategoryRequest;
import com.mrbt.orbit.ledger.api.request.UpdateCategoryRequest;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.model.enums.CategoryStatus;
import com.mrbt.orbit.ledger.core.model.enums.CategoryType;
import com.mrbt.orbit.ledger.core.port.in.CreateCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.DeleteCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.GetCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateCategoryUseCase;
import com.mrbt.orbit.ledger.api.mapper.CategoryDtoMapper;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(CategoryDtoMapper.class)
class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private CreateCategoryUseCase createCategoryUseCase;

	@MockitoBean
	private GetCategoryUseCase getCategoryUseCase;

	@MockitoBean
	private UpdateCategoryUseCase updateCategoryUseCase;

	@MockitoBean
	private DeleteCategoryUseCase deleteCategoryUseCase;

	@Test
	void createCategory_returns201() throws Exception {
		UUID catId = UUID.randomUUID();
		when(createCategoryUseCase.createCategory(any(Category.class))).thenAnswer(inv -> {
			Category c = inv.getArgument(0);
			c.setId(catId);
			c.setIsSystem(false);
			return c;
		});

		CreateCategoryRequest request = CreateCategoryRequest.builder().userId(UUID.randomUUID()).name("Food")
				.type(CategoryType.EXPENSE).icon("utensils").color("#FF0000").build();

		mockMvc.perform(post("/api/v1/categories").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("Food"));
	}

	@Test
	void getSystemCategories_returns200() throws Exception {
		List<Category> categories = List.of(Category.builder().id(UUID.randomUUID()).name("Food")
				.type(CategoryType.EXPENSE).isSystem(true).build());

		when(getCategoryUseCase.getSystemCategories()).thenReturn(categories);

		mockMvc.perform(get("/api/v1/categories/system")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].name").value("Food"));
	}

	@Test
	void getUserCategories_returns200() throws Exception {
		UUID userId = UUID.randomUUID();
		List<Category> categories = List
				.of(Category.builder().id(UUID.randomUUID()).name("Custom").type(CategoryType.EXPENSE).build());

		when(getCategoryUseCase.getUserCategories(userId)).thenReturn(categories);

		mockMvc.perform(get("/api/v1/categories/user/{userId}", userId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].name").value("Custom"));
	}

	@Test
	void updateCategory_returns200() throws Exception {
		UUID categoryId = UUID.randomUUID();
		Category updated = Category.builder().id(categoryId).name("New Name").type(CategoryType.EXPENSE)
				.status(CategoryStatus.ACTIVE).build();

		when(updateCategoryUseCase.updateCategory(eq(categoryId), eq("New Name"), isNull())).thenReturn(updated);

		UpdateCategoryRequest request = UpdateCategoryRequest.builder().name("New Name").build();

		mockMvc.perform(patch("/api/v1/categories/{categoryId}", categoryId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.name").value("New Name"));
	}

	@Test
	void deleteCategory_returns200() throws Exception {
		UUID categoryId = UUID.randomUUID();

		mockMvc.perform(delete("/api/v1/categories/{categoryId}", categoryId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true));
	}

}
