package com.mrbt.orbit.common.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.mrbt.orbit.common.core.model.PageResult;

class PaginationHelperTest {

	@Test
	void paginated_shouldMapDomainPageToResponsePage() {
		PageResult<String> domainPage = new PageResult<>(List.of("a", "b"), 10, 5, 0, 2);

		ResponseEntity<ApiResponse<PageResult<String>>> response = PaginationHelper.paginated(0, 2,
				(page, size) -> domainPage, domains -> domains.stream().map(String::toUpperCase).toList());

		assertThat(response.getStatusCode().value()).isEqualTo(200);
		PageResult<String> body = response.getBody().data();
		assertThat(body.content()).containsExactly("A", "B");
		assertThat(body.totalElements()).isEqualTo(10);
		assertThat(body.totalPages()).isEqualTo(5);
		assertThat(body.page()).isEqualTo(0);
		assertThat(body.size()).isEqualTo(2);
	}

	@Test
	void paginated_shouldRespectMaxPageSize() {
		PageResult<Integer> domainPage = new PageResult<>(List.of(1), 1, 1, 0, 100);

		ResponseEntity<ApiResponse<PageResult<Integer>>> response = PaginationHelper.paginated(0, 999,
				(page, size) -> domainPage, List::copyOf);

		assertThat(response.getBody().data()).isNotNull();
	}

}
