package com.mrbt.orbit.security.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.response.UserResponse;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.model.enums.UserStatus;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;

class UserMapperTest {

	private final UserMapper mapper = new UserMapper();

	@Test
	void toDomain_fromEntity_mapsAllFields() {
		UserEntity entity = new UserEntity();
		UUID id = UUID.randomUUID();
		entity.setId(id);
		entity.setClerkUserId("clerk_123");
		entity.setEmail("test@test.com");
		entity.setFirstName("John");
		entity.setLastName("Doe");
		entity.setBaseCurrency("USD");
		entity.setTimezone("Asia/Bangkok");
		entity.setStatus(UserStatus.ACTIVE);
		entity.setCreatedAt(Instant.parse("2026-01-15T12:00:00Z"));
		entity.setUpdatedAt(Instant.parse("2026-01-16T12:00:00Z"));

		User result = mapper.toDomain(entity);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getClerkUserId()).isEqualTo("clerk_123");
		assertThat(result.getEmail()).isEqualTo("test@test.com");
		assertThat(result.getFirstName()).isEqualTo("John");
		assertThat(result.getLastName()).isEqualTo("Doe");
		assertThat(result.getBaseCurrency()).isEqualTo("USD");
		assertThat(result.getTimezone()).isEqualTo("Asia/Bangkok");
		assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();
	}

	@Test
	void toDomain_fromEntity_returnsNullForNull() {
		assertThat(mapper.toDomain((UserEntity) null)).isNull();
	}

	@Test
	void toEntity_mapsAllFields() {
		UUID id = UUID.randomUUID();
		User domain = User.builder().id(id).clerkUserId("clerk_123").email("test@test.com").firstName("John")
				.lastName("Doe").baseCurrency("USD").timezone("Asia/Bangkok").status(UserStatus.ACTIVE).build();

		UserEntity result = mapper.toEntity(domain);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getClerkUserId()).isEqualTo("clerk_123");
		assertThat(result.getEmail()).isEqualTo("test@test.com");
		assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
	}

	@Test
	void toEntity_returnsNullForNull() {
		assertThat(mapper.toEntity(null)).isNull();
	}

	@Test
	void toDomain_fromRequest_mapsFields() {
		CreateUserRequest request = CreateUserRequest.builder().clerkUserId("clerk_456").email("new@test.com")
				.firstName("Jane").lastName("Smith").baseCurrency("THB").timezone("Asia/Bangkok").build();

		User result = mapper.toDomain(request);

		assertThat(result.getClerkUserId()).isEqualTo("clerk_456");
		assertThat(result.getEmail()).isEqualTo("new@test.com");
		assertThat(result.getFirstName()).isEqualTo("Jane");
		assertThat(result.getBaseCurrency()).isEqualTo("THB");
	}

	@Test
	void toDomain_fromRequest_returnsNullForNull() {
		assertThat(mapper.toDomain((CreateUserRequest) null)).isNull();
	}

	@Test
	void toResponse_mapsFields() {
		User domain = User.builder().id(UUID.randomUUID()).email("test@test.com").firstName("John").lastName("Doe")
				.baseCurrency("USD").timezone("UTC").status(UserStatus.ACTIVE).build();

		UserResponse result = mapper.toResponse(domain);

		assertThat(result.email()).isEqualTo("test@test.com");
		assertThat(result.status()).isEqualTo("ACTIVE");
	}

	@Test
	void toResponse_returnsNullForNull() {
		assertThat(mapper.toResponse(null)).isNull();
	}

	@Test
	void toResponse_handlesNullStatus() {
		User domain = User.builder().id(UUID.randomUUID()).email("test@test.com").build();

		UserResponse result = mapper.toResponse(domain);

		assertThat(result.status()).isNull();
	}

}
