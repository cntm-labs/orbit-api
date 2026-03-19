package com.mrbt.orbit.security.api;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.request.UpdateUserRequest;
import com.mrbt.orbit.security.api.response.UserResponse;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.in.CreateUserUseCase;
import com.mrbt.orbit.security.core.port.in.DeleteUserUseCase;
import com.mrbt.orbit.security.core.port.in.GetUserUseCase;
import com.mrbt.orbit.security.core.port.in.UpdateUserUseCase;
import com.mrbt.orbit.security.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management and Clerk synchronization endpoints")
public class UserController {

	private final CreateUserUseCase createUserUseCase;
	private final GetUserUseCase getUserUseCase;
	private final UpdateUserUseCase updateUserUseCase;
	private final DeleteUserUseCase deleteUserUseCase;
	private final UserMapper userMapper;

	@PostMapping
	@Operation(summary = "Register a new user", description = "Syncs a newly registered Clerk user into the local database")
	public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody CreateUserRequest request) {
		User domainUser = userMapper.toDomain(request);
		User createdUser = createUserUseCase.createUser(domainUser);
		UserResponse response = userMapper.toResponse(createdUser);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.success("User registered successfully", response));
	}

	@GetMapping("/clerk/{clerkUserId}")
	@Operation(summary = "Get user by Clerk ID", description = "Retrieves a local user profile using their external Clerk ID")
	public ResponseEntity<ApiResponse<UserResponse>> getUserByClerkId(@PathVariable String clerkUserId) {
		User user = getUserUseCase.getUserByClerkId(clerkUserId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Clerk ID", clerkUserId));
		return ResponseEntity.ok(ApiResponse.success(userMapper.toResponse(user)));
	}

	@PatchMapping("/{userId}")
	@Operation(summary = "Update user profile", description = "Partially updates user profile fields")
	public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable UUID userId,
			@Valid @RequestBody UpdateUserRequest request) {
		User updated = updateUserUseCase.updateUser(userId, request.firstName(), request.lastName(),
				request.baseCurrency(), request.timezone());
		return ResponseEntity.ok(ApiResponse.success("User updated", userMapper.toResponse(updated)));
	}

	@DeleteMapping("/{userId}")
	@Operation(summary = "Deactivate a user", description = "Soft deletes a user by setting status to DEACTIVATED")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID userId) {
		deleteUserUseCase.deactivateUser(userId);
		return ResponseEntity.ok(ApiResponse.success("User deactivated", null));
	}
}
