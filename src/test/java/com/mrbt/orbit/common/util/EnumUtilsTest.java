package com.mrbt.orbit.common.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EnumUtilsTest {

	private enum TestStatus {

		ACTIVE, PAUSED

	}

	@Test
	void toStringOrNull_shouldReturnNameForNonNull() {
		assertThat(EnumUtils.toStringOrNull(TestStatus.ACTIVE)).isEqualTo("ACTIVE");
	}

	@Test
	void toStringOrNull_shouldReturnNullForNull() {
		assertThat(EnumUtils.toStringOrNull(null)).isNull();
	}

	@Test
	void fromStringOrNull_shouldReturnEnumForValidString() {
		assertThat(EnumUtils.fromStringOrNull("PAUSED", TestStatus.class)).isEqualTo(TestStatus.PAUSED);
	}

	@Test
	void fromStringOrNull_shouldReturnNullForNullString() {
		assertThat(EnumUtils.fromStringOrNull(null, TestStatus.class)).isNull();
	}

}
