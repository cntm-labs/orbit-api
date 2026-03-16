package com.mrbt.orbit.security.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import com.mrbt.orbit.security.infrastructure.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

	@Mock
	private UserRepository springDataRepository;

	@Mock
	private UserMapper mapper;

	@InjectMocks
	private UserRepositoryAdapter adapter;

	@Test
	void save_convertsAndPersists() {
		User domain = User.builder().email("test@test.com").build();
		UserEntity entity = new UserEntity();
		UserEntity savedEntity = new UserEntity();
		User expected = User.builder().id(UUID.randomUUID()).email("test@test.com").build();

		when(mapper.toEntity(domain)).thenReturn(entity);
		when(springDataRepository.save(entity)).thenReturn(savedEntity);
		when(mapper.toDomain(savedEntity)).thenReturn(expected);

		assertThat(adapter.save(domain)).isEqualTo(expected);
	}

	@Test
	void findByClerkUserId_returnsUser() {
		UserEntity entity = new UserEntity();
		User expected = User.builder().clerkUserId("clerk_123").build();

		when(springDataRepository.findByClerkUserId("clerk_123")).thenReturn(Optional.of(entity));
		when(mapper.toDomain(entity)).thenReturn(expected);

		assertThat(adapter.findByClerkUserId("clerk_123")).isPresent().contains(expected);
	}

	@Test
	void findByEmail_returnsUser() {
		UserEntity entity = new UserEntity();
		User expected = User.builder().email("test@test.com").build();

		when(springDataRepository.findByEmail("test@test.com")).thenReturn(Optional.of(entity));
		when(mapper.toDomain(entity)).thenReturn(expected);

		assertThat(adapter.findByEmail("test@test.com")).isPresent().contains(expected);
	}

	@Test
	void existsByClerkUserId_delegatesToRepo() {
		when(springDataRepository.existsByClerkUserId("clerk_123")).thenReturn(true);

		assertThat(adapter.existsByClerkUserId("clerk_123")).isTrue();
	}

	@Test
	void existsByEmail_delegatesToRepo() {
		when(springDataRepository.existsByEmail("test@test.com")).thenReturn(false);

		assertThat(adapter.existsByEmail("test@test.com")).isFalse();
	}

}
