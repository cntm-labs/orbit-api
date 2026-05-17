package com.mrbt.orbit.security.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrbt.orbit.common.api.ApiResponse;
import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.request.LoginRequest;
import com.mrbt.orbit.security.api.response.AuthResponse;
import com.mrbt.orbit.security.core.port.in.AuthUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user login and registration")
public class AuthController {

	private final AuthUseCase authUseCase;

	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody CreateUserRequest request) {
		return ResponseEntity.ok(ApiResponse.success("User registered successfully", authUseCase.register(request)));
	}

	@PostMapping("/login")
	@Operation(summary = "Authenticate user and return JWT")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(ApiResponse.success("Login successful", authUseCase.login(request)));
	}
}
