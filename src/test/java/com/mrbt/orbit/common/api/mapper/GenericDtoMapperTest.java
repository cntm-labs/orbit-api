package com.mrbt.orbit.common.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class GenericDtoMapperTest {

	private final TestDtoMapper mapper = new TestDtoMapper();

	@Test
	void toResponseList_shouldMapAllElements() {
		List<String> responses = mapper.toResponseList(List.of("a", "b", "c"));
		assertThat(responses).containsExactly("A", "B", "C");
	}

	@Test
	void toResponseList_shouldReturnEmptyListForNull() {
		List<String> responses = mapper.toResponseList(null);
		assertThat(responses).isEmpty();
	}

	@Test
	void toDomainList_shouldMapAllElements() {
		List<String> domains = mapper.toDomainList(List.of("X", "Y"));
		assertThat(domains).containsExactly("x", "y");
	}

	@Test
	void toDomainList_shouldReturnEmptyListForNull() {
		List<String> domains = mapper.toDomainList(null);
		assertThat(domains).isEmpty();
	}

	static class TestDtoMapper extends GenericDtoMapper<String, String, String> {

		@Override
		public String toDomain(String request) {
			return request == null ? null : request.toLowerCase();
		}

		@Override
		public String toResponse(String domain) {
			return domain == null ? null : domain.toUpperCase();
		}

	}

}
