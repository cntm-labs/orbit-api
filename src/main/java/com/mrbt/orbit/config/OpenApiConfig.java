package com.mrbt.orbit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(
				new Info().title("Orbit PFM API").description("Personal Finance Management System").version("1.0.0"));
	}

}
