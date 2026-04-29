package com.mrbt.orbit.security.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.core.model.enums.UserStatus;

class UserTest {

	@Test
	void deactivate_shouldSetStatusToDeactivated() {
		User user = User.builder().status(UserStatus.ACTIVE).build();
		user.deactivate();
		assertThat(user.getStatus()).isEqualTo(UserStatus.DEACTIVATED);
	}

}
