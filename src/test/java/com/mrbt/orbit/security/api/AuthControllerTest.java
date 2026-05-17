package com.mrbt.orbit.security.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.orbit.security.api.request.LoginRequest;
import com.mrbt.orbit.security.api.response.AuthResponse;
import com.mrbt.orbit.security.core.port.in.AuthUseCase;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private AuthUseCase authUseCase;

	@Test
	void login_ShouldReturnToken() throws Exception {
		LoginRequest request = new LoginRequest("test@example.com", "password");
		AuthResponse response = new AuthResponse("mock-jwt-token", 3600L, UUID.randomUUID(), "test@example.com");

		when(authUseCase.login(any(LoginRequest.class))).thenReturn(response);

		mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.token").value("mock-jwt-token"));
	}
}
