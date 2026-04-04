package com.mrbt.orbit.security.core.service;

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
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.model.enums.UserStatus;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class DeactivateUserServiceTest {

	@Mock
	private UserRepositoryPort userRepositoryPort;

	@InjectMocks
	private DeactivateUserService deactivateUserService;

	@Test
	void deactivateUser_setsStatusToDeactivated() {
		UUID userId = UUID.randomUUID();
		User user = User.builder().id(userId).firstName("John").email("john@test.com").status(UserStatus.ACTIVE)
				.build();

		when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(user));
		when(userRepositoryPort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

		deactivateUserService.deactivateUser(userId);

		assertThat(user.getStatus()).isEqualTo(UserStatus.DEACTIVATED);
		verify(userRepositoryPort).save(user);
	}

	@Test
	void deactivateUser_throwsWhenNotFound() {
		UUID userId = UUID.randomUUID();
		when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> deactivateUserService.deactivateUser(userId))
				.isInstanceOf(ResourceNotFoundException.class);
	}

}
