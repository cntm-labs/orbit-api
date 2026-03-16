package com.mrbt.orbit.common.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ApiResponseTest {

	@Test
	void success_withData_setsFieldsCorrectly() {
		ApiResponse<String> response = ApiResponse.success("hello");

		assertThat(response.success()).isTrue();
		assertThat(response.data()).isEqualTo("hello");
		assertThat(response.message()).isNull();
		assertThat(response.timestamp()).isNotNull();
	}

	@Test
	void success_withMessageAndData_setsFieldsCorrectly() {
		ApiResponse<Integer> response = ApiResponse.success("Created", 42);

		assertThat(response.success()).isTrue();
		assertThat(response.message()).isEqualTo("Created");
		assertThat(response.data()).isEqualTo(42);
	}

	@Test
	void error_setsFieldsCorrectly() {
		ApiResponse<Void> response = ApiResponse.error("Something went wrong");

		assertThat(response.success()).isFalse();
		assertThat(response.message()).isEqualTo("Something went wrong");
		assertThat(response.data()).isNull();
		assertThat(response.timestamp()).isNotNull();
	}

}
