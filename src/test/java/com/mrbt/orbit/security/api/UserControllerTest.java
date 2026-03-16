package com.mrbt.orbit.security.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.orbit.security.api.request.CreateUserRequest;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.model.enums.UserStatus;
import com.mrbt.orbit.security.core.port.in.CreateUserUseCase;
import com.mrbt.orbit.security.core.port.in.GetUserUseCase;
import com.mrbt.orbit.security.infrastructure.mapper.UserMapper;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserMapper.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private CreateUserUseCase createUserUseCase;

	@MockitoBean
	private GetUserUseCase getUserUseCase;

	@Test
	void registerUser_returns201() throws Exception {
		UUID userId = UUID.randomUUID();
		when(createUserUseCase.createUser(any(User.class))).thenAnswer(inv -> {
			User u = inv.getArgument(0);
			u.setId(userId);
			u.setStatus(UserStatus.ACTIVE);
			u.setBaseCurrency("USD");
			return u;
		});

		CreateUserRequest request = CreateUserRequest.builder().clerkUserId("clerk_123").email("test@example.com")
				.firstName("John").lastName("Doe").build();

		mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.email").value("test@example.com"));
	}

	@Test
	void getUserByClerkId_returns200() throws Exception {
		User user = User.builder().id(UUID.randomUUID()).clerkUserId("clerk_abc").email("test@test.com")
				.status(UserStatus.ACTIVE).build();

		when(getUserUseCase.getUserByClerkId("clerk_abc")).thenReturn(Optional.of(user));

		mockMvc.perform(get("/api/v1/users/clerk/{clerkUserId}", "clerk_abc")).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.email").value("test@test.com"));
	}

	@Test
	void getUserByClerkId_returns404WhenNotFound() throws Exception {
		when(getUserUseCase.getUserByClerkId("nonexistent")).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/users/clerk/{clerkUserId}", "nonexistent")).andExpect(status().isNotFound());
	}

}
