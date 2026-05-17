package com.mrbt.orbit.security.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateUserRequest(String clerkUserId,

		@NotBlank(message = "Email cannot be blank") @Email(message = "Must be a valid email format") String email,

		@NotBlank(message = "Password cannot be blank") String password,

		String firstName, String lastName, String baseCurrency, String timezone) {
}
