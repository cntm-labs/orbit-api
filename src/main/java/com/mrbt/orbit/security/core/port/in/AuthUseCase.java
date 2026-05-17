package com.mrbt.orbit.security.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.request.LoginRequest;
import com.mrbt.orbit.security.api.response.AuthResponse;

public interface AuthUseCase extends UseCase {
	AuthResponse register(CreateUserRequest request);
	AuthResponse login(LoginRequest request);
}
