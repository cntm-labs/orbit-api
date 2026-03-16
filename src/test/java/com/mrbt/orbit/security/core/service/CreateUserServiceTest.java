package com.mrbt.orbit.security.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.DuplicateResourceException;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.model.enums.UserStatus;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

	@Mock
	private UserRepositoryPort userRepositoryPort;

	@InjectMocks
	private CreateUserService createUserService;

	@Test
	void createUser_setsDefaultStatusAndCurrency() {
		User user = User.builder().clerkUserId("clerk_123").email("test@example.com").build();

		when(userRepositoryPort.existsByClerkUserId("clerk_123")).thenReturn(false);
		when(userRepositoryPort.existsByEmail("test@example.com")).thenReturn(false);
		when(userRepositoryPort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

		User result = createUserService.createUser(user);

		assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(result.getBaseCurrency()).isEqualTo("USD");
	}

	@Test
	void createUser_preservesExplicitStatusAndCurrency() {
		User user = User.builder().clerkUserId("clerk_456").email("user@test.com").status(UserStatus.SUSPENDED)
				.baseCurrency("THB").build();

		when(userRepositoryPort.existsByClerkUserId("clerk_456")).thenReturn(false);
		when(userRepositoryPort.existsByEmail("user@test.com")).thenReturn(false);
		when(userRepositoryPort.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

		User result = createUserService.createUser(user);

		assertThat(result.getStatus()).isEqualTo(UserStatus.SUSPENDED);
		assertThat(result.getBaseCurrency()).isEqualTo("THB");
	}

	@Test
	void createUser_throwsOnDuplicateClerkId() {
		User user = User.builder().clerkUserId("clerk_dup").email("new@test.com").build();

		when(userRepositoryPort.existsByClerkUserId("clerk_dup")).thenReturn(true);

		assertThatThrownBy(() -> createUserService.createUser(user)).isInstanceOf(DuplicateResourceException.class)
				.hasMessageContaining("Clerk ID");

		verify(userRepositoryPort, never()).save(any());
	}

	@Test
	void createUser_throwsOnDuplicateEmail() {
		User user = User.builder().clerkUserId("clerk_new").email("dup@test.com").build();

		when(userRepositoryPort.existsByClerkUserId("clerk_new")).thenReturn(false);
		when(userRepositoryPort.existsByEmail("dup@test.com")).thenReturn(true);

		assertThatThrownBy(() -> createUserService.createUser(user)).isInstanceOf(DuplicateResourceException.class)
				.hasMessageContaining("Email");

		verify(userRepositoryPort, never()).save(any());
	}

}
