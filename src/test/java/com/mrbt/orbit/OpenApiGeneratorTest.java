package com.mrbt.orbit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OpenApiGeneratorTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void generateOpenApiSpec() throws Exception {
		MvcResult result = mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk()).andReturn();

		String json = result.getResponse().getContentAsString();
		Files.createDirectories(Paths.get("docs/public"));
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("docs/public/openapi.json"),
				StandardCharsets.UTF_8)) {
			writer.write(json);
		}
		System.out.println("✅ OpenAPI spec generated successfully in docs/public/openapi.json");
	}
}
