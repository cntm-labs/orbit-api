package com.mrbt.orbit.security.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

	@Mock
	private UserRepositoryPort userRepositoryPort;

	@InjectMocks
	private GetUserService getUserService;

	@Test
	void getUserByClerkId_returnsUser() {
		User user = User.builder().clerkUserId("clerk_123").email("test@example.com").build();

		when(userRepositoryPort.findByClerkUserId("clerk_123")).thenReturn(Optional.of(user));

		Optional<User> result = getUserService.getUserByClerkId("clerk_123");

		assertThat(result).isPresent();
		assertThat(result.get().getEmail()).isEqualTo("test@example.com");
		verify(userRepositoryPort).findByClerkUserId("clerk_123");
	}

	@Test
	void getUserByClerkId_returnsEmptyWhenNotFound() {
		when(userRepositoryPort.findByClerkUserId("nonexistent")).thenReturn(Optional.empty());

		assertThat(getUserService.getUserByClerkId("nonexistent")).isEmpty();
	}

}
