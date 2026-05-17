package com.mrbt.orbit.security.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mrbt.orbit.security.api.mapper.UserProfileDtoMapper;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.port.in.UserProfileUseCase;

@WebMvcTest(UserProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserProfileDtoMapper.class)
class UserProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserProfileUseCase userProfileUseCase;

	@Test
	void getAddresses_ShouldReturnList() throws Exception {
		UUID userId = UUID.randomUUID();
		UserAddress address = UserAddress.builder().id(UUID.randomUUID()).userId(userId).label("Home").build();
		when(userProfileUseCase.getUserAddresses(userId)).thenReturn(List.of(address));

		mockMvc.perform(get("/api/v1/users/" + userId + "/profile/addresses").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data[0].label").value("Home"));
	}

	@Test
	void addAddress_ShouldCreateAddress() throws Exception {
		UUID userId = UUID.randomUUID();
		String requestJson = """
				{
				  "label": "Work",
				  "addressLine1": "Street 1",
				  "city": "City",
				  "postalCode": "12345",
				  "countryCode": "US"
				}
				""";
		UserAddress address = UserAddress.builder().id(UUID.randomUUID()).userId(userId).label("Work").build();

		when(userProfileUseCase.addAddress(any(UserAddress.class))).thenReturn(address);

		mockMvc.perform(post("/api/v1/users/" + userId + "/profile/addresses").contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)).andExpect(status().isOk()).andExpect(jsonPath("$.data.label").value("Work"));
	}
}
