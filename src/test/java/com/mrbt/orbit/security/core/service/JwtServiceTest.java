package com.mrbt.orbit.security.core.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtServiceTest {

	private JwtService jwtService;

	@BeforeEach
	void setUp() {
		jwtService = new JwtService();
		ReflectionTestUtils.setField(jwtService, "secretKey",
				"404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
		ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L);
	}

	@Test
	void generateAndValidateToken() {
		UserDetails userDetails = new User("test@example.com", "password", Collections.emptyList());

		String token = jwtService.generateToken(userDetails);
		assertThat(token).isNotBlank();

		String username = jwtService.extractUsername(token);
		assertThat(username).isEqualTo("test@example.com");

		assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();
	}
}
