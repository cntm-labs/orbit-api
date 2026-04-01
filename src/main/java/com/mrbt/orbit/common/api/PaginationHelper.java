package com.mrbt.orbit.common.api;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;

import com.mrbt.orbit.common.core.model.PageResult;

/**
 * Utility to eliminate duplicate pagination response wrapping across
 * controllers.
 */
public final class PaginationHelper {

	private PaginationHelper() {
	}

	public static <D, R> ResponseEntity<ApiResponse<PageResult<R>>> paginated(int page, int size,
			BiFunction<Integer, Integer, PageResult<D>> fetcher, Function<List<D>, List<R>> mapper) {
		PaginationParams params = PaginationParams.of(page, size);
		PageResult<D> result = fetcher.apply(params.page(), params.size());
		PageResult<R> mapped = new PageResult<>(mapper.apply(result.content()), result.totalElements(),
				result.totalPages(), result.page(), result.size());
		return ResponseEntity.ok(ApiResponse.success(mapped));
	}

}
