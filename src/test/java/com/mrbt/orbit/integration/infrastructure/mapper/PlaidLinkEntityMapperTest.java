package com.mrbt.orbit.integration.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.infrastructure.entity.PlaidLinkEntity;

class PlaidLinkEntityMapperTest {

	private final PlaidLinkEntityMapper mapper = new PlaidLinkEntityMapper();

	@Test
	void toDomain_ShouldMapCorrectly() {
		PlaidLinkEntity entity = new PlaidLinkEntity();
		entity.setId(UUID.randomUUID());
		entity.setInstitutionName("Chase");

		PlaidLink domain = mapper.toDomain(entity);

		assertThat(domain).isNotNull();
		assertThat(domain.getInstitutionName()).isEqualTo(entity.getInstitutionName());
	}
}
