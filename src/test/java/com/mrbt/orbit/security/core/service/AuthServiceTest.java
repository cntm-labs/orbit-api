package com.mrbt.orbit.security.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.api.request.LoginRequest;
import com.mrbt.orbit.security.api.response.AuthResponse;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.in.CreateUserUseCase;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;
import com.mrbt.orbit.security.infrastructure.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private UserRepositoryPort userRepositoryPort;
	@Mock
	private CreateUserUseCase createUserUseCase;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtService jwtService;
	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private AuthService authService;

	@Test
	void register_ShouldReturnAuthResponse() {
		// Given
		CreateUserRequest request = new CreateUserRequest(null, "test@example.com", "password123", "First", "Last",
				"USD", "UTC");
		User user = User.builder().email("test@example.com").build();
		User savedUser = User.builder().id(UUID.randomUUID()).email("test@example.com").build();

		when(userMapper.toDomain(request)).thenReturn(user);
		when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
		when(createUserUseCase.createUser(user)).thenReturn(savedUser);
		when(jwtService.generateToken(any())).thenReturn("mockToken");
		when(jwtService.getExpirationTime()).thenReturn(3600L);

		// When
		AuthResponse result = authService.register(request);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.token()).isEqualTo("mockToken");
		assertThat(result.email()).isEqualTo("test@example.com");
		verify(createUserUseCase).createUser(user);
	}

	@Test
	void login_ShouldReturnAuthResponse() {
		// Given
		LoginRequest request = new LoginRequest("test@example.com", "password123");
		User user = User.builder().id(UUID.randomUUID()).email("test@example.com").password("encoded").build();

		when(userRepositoryPort.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(user));
		when(jwtService.generateToken(any())).thenReturn("mockToken");
		when(jwtService.getExpirationTime()).thenReturn(3600L);

		// When
		AuthResponse result = authService.login(request);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.token()).isEqualTo("mockToken");
		verify(authenticationManager).authenticate(any());
	}

}
