package com.mrbt.orbit.integration.api;

import static org.mockito.ArgumentMatchers.eq;
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

import com.mrbt.orbit.integration.api.mapper.IntegrationDtoMapper;
import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.core.port.in.PlaidLinkUseCase;

@WebMvcTest(PlaidLinkController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(IntegrationDtoMapper.class)
class PlaidLinkControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private PlaidLinkUseCase plaidLinkUseCase;

	@Test
	void getLinksByUser_ShouldReturnList() throws Exception {
		UUID userId = UUID.randomUUID();
		PlaidLink link = PlaidLink.builder().id(UUID.randomUUID()).userId(userId).institutionName("Chase").build();
		when(plaidLinkUseCase.getLinksByUser(userId)).thenReturn(List.of(link));

		mockMvc.perform(get("/api/v1/plaid/links/user/" + userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data[0].institutionName").value("Chase"));
	}

	@Test
	void createLink_ShouldReturnLink() throws Exception {
		UUID userId = UUID.randomUUID();
		String requestJson = """
				{
				  "userId": "%s",
				  "publicToken": "tok_123"
				}
				""".formatted(userId);
		PlaidLink link = PlaidLink.builder().id(UUID.randomUUID()).userId(userId).institutionName("Chase").build();

		when(plaidLinkUseCase.createLink(eq(userId), eq("tok_123"))).thenReturn(link);

		mockMvc.perform(post("/api/v1/plaid/link").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.userId").value(userId.toString()));
	}
}
