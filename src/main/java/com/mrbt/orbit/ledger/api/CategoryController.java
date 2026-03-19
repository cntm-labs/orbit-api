package com.mrbt.orbit.ledger.api;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.ledger.api.request.CreateCategoryRequest;
import com.mrbt.orbit.ledger.api.request.UpdateCategoryRequest;
import com.mrbt.orbit.ledger.api.response.CategoryResponse;
import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.port.in.CreateCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.DeleteCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.GetCategoryUseCase;
import com.mrbt.orbit.ledger.core.port.in.UpdateCategoryUseCase;
import com.mrbt.orbit.ledger.infrastructure.mapper.CategoryMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Manage system-wide and user-specific transaction categories")
public class CategoryController {

	private final CreateCategoryUseCase createCategoryUseCase;
	private final GetCategoryUseCase getCategoryUseCase;
	private final UpdateCategoryUseCase updateCategoryUseCase;
	private final DeleteCategoryUseCase deleteCategoryUseCase;
	private final CategoryMapper categoryMapper;

	@PostMapping
	@Operation(summary = "Create a category", description = "Creates a new category. If userId is omitted, it becomes a system category.")
	public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
			@Valid @RequestBody CreateCategoryRequest request) {
		Category domainCategory = Category.builder().userId(request.userId()).name(request.name()).type(request.type())
				.icon(request.icon()).color(request.color()).parentCategoryId(request.parentCategoryId()).build();

		Category createdCategory = createCategoryUseCase.createCategory(domainCategory);
		CategoryResponse response = categoryMapper.toResponse(createdCategory);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Category created successfully", response));
	}

	@GetMapping("/system")
	@Operation(summary = "Get system categories", description = "Retrieves all default system categories available to all users")
	public ResponseEntity<ApiResponse<List<CategoryResponse>>> getSystemCategories() {
		List<Category> categories = getCategoryUseCase.getSystemCategories();
		List<CategoryResponse> responses = categories.stream().map(categoryMapper::toResponse)
				.collect(Collectors.toList());

		return ResponseEntity.ok(ApiResponse.success(responses));
	}

	@GetMapping("/user/{userId}")
	@Operation(summary = "Get user categories", description = "Retrieves all custom categories created by a specific user")
	public ResponseEntity<ApiResponse<List<CategoryResponse>>> getUserCategories(@PathVariable UUID userId) {
		List<Category> categories = getCategoryUseCase.getUserCategories(userId);
		List<CategoryResponse> responses = categories.stream().map(categoryMapper::toResponse)
				.collect(Collectors.toList());

		return ResponseEntity.ok(ApiResponse.success(responses));
	}

	@PatchMapping("/{categoryId}")
	@Operation(summary = "Update a category", description = "Partially updates category fields")
	public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable UUID categoryId,
			@Valid @RequestBody UpdateCategoryRequest request) {
		Category updated = updateCategoryUseCase.updateCategory(categoryId, request.name(), request.parentCategoryId());
		return ResponseEntity.ok(ApiResponse.success("Category updated", categoryMapper.toResponse(updated)));
	}

	@DeleteMapping("/{categoryId}")
	@Operation(summary = "Delete a category", description = "Soft deletes a category by setting status to INACTIVE")
	public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable UUID categoryId) {
		deleteCategoryUseCase.deleteCategory(categoryId);
		return ResponseEntity.ok(ApiResponse.success("Category deleted", null));
	}
}
