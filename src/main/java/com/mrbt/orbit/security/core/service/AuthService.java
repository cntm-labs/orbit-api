package com.mrbt.orbit.security.core.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.request.LoginRequest;
import com.mrbt.orbit.security.api.response.AuthResponse;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.in.AuthUseCase;
import com.mrbt.orbit.security.core.port.in.CreateUserUseCase;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;
import com.mrbt.orbit.security.infrastructure.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

	private final UserRepositoryPort userRepositoryPort;
	private final CreateUserUseCase createUserUseCase;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserMapper userMapper;

	@Override
	@Transactional
	public AuthResponse register(CreateUserRequest request) {
		User user = userMapper.toDomain(request);
		user.setPassword(passwordEncoder.encode(request.password()));

		User savedUser = createUserUseCase.createUser(user);

		String token = jwtService.generateToken(new UserDetailsServiceImpl.InternalUserDetails(savedUser));

		return new AuthResponse(token, jwtService.getExpirationTime(), savedUser.getId(), savedUser.getEmail());
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

		User user = userRepositoryPort.findByEmail(request.email())
				.orElseThrow(() -> new ResourceNotFoundException("User", "Email", request.email()));

		String token = jwtService.generateToken(new UserDetailsServiceImpl.InternalUserDetails(user));

		return new AuthResponse(token, jwtService.getExpirationTime(), user.getId(), user.getEmail());
	}
}
