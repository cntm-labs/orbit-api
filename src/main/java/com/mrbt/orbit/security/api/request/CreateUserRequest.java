package com.mrbt.orbit.security.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateUserRequest(@NotBlank(message = "Clerk User ID cannot be blank") String clerkUserId,

		@NotBlank(message = "Email cannot be blank") @Email(message = "Must be a valid email format") String email,

		String firstName, String lastName, String baseCurrency, String timezone) {
}
