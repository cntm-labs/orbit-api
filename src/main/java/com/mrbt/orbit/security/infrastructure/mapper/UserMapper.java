package com.mrbt.orbit.security.infrastructure.mapper;

import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.response.UserResponse;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Manual mapper until MapStruct is fully configured. Follows the standard of
 * separating Entities, Domains, and DTOs.
 */
@Component
public class UserMapper {

	// --- Entity <-> Domain ---

	public User toDomain(UserEntity entity) {
		if (entity == null)
			return null;

		return User.builder().id(entity.getId()).clerkUserId(entity.getClerkUserId()).email(entity.getEmail())
				.firstName(entity.getFirstName()).lastName(entity.getLastName()).baseCurrency(entity.getBaseCurrency())
				.timezone(entity.getTimezone()).status(entity.getStatus())
				.createdAt(
						entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(java.time.ZoneOffset.UTC) : null)
				.updatedAt(
						entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(java.time.ZoneOffset.UTC) : null)
				.build();
	}

	public UserEntity toEntity(User domain) {
		if (domain == null)
			return null;

		UserEntity entity = new UserEntity();
		entity.setId(domain.getId());
		entity.setClerkUserId(domain.getClerkUserId());
		entity.setEmail(domain.getEmail());
		entity.setFirstName(domain.getFirstName());
		entity.setLastName(domain.getLastName());
		entity.setBaseCurrency(domain.getBaseCurrency());
		entity.setTimezone(domain.getTimezone());
		entity.setStatus(domain.getStatus());

		return entity;
	}

	// --- Domain <-> DTO ---

	public User toDomain(CreateUserRequest request) {
		if (request == null)
			return null;

		return User.builder().clerkUserId(request.clerkUserId()).email(request.email()).firstName(request.firstName())
				.lastName(request.lastName()).baseCurrency(request.baseCurrency()).timezone(request.timezone()).build();
	}

	public UserResponse toResponse(User domain) {
		if (domain == null)
			return null;

		return UserResponse.builder().id(domain.getId()).email(domain.getEmail()).firstName(domain.getFirstName())
				.lastName(domain.getLastName()).baseCurrency(domain.getBaseCurrency()).timezone(domain.getTimezone())
				.status(domain.getStatus() != null ? domain.getStatus().name() : null).createdAt(domain.getCreatedAt())
				.build();
	}
}
