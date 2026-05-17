package com.mrbt.orbit.integration.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.infrastructure.entity.PlaidLinkEntity;

@Component
public class PlaidLinkEntityMapper extends AbstractNullSafeMapper<PlaidLinkEntity, PlaidLink> {

	@Override
	public PlaidLink toDomain(PlaidLinkEntity entity) {
		if (entity == null) {
			return null;
		}
		return PlaidLink.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.userId(entity.getUserId()).plaidItemId(entity.getPlaidItemId())
				.accessTokenEncrypted(entity.getAccessTokenEncrypted()).institutionName(entity.getInstitutionName())
				.syncCursor(entity.getSyncCursor()).status(entity.getStatus()).errorCode(entity.getErrorCode()).build();
	}

	@Override
	public PlaidLinkEntity toEntity(PlaidLink domain) {
		if (domain == null) {
			return null;
		}
		PlaidLinkEntity entity = new PlaidLinkEntity();
		entity.setId(domain.getId());
		entity.setUserId(domain.getUserId());
		entity.setPlaidItemId(domain.getPlaidItemId());
		entity.setAccessTokenEncrypted(domain.getAccessTokenEncrypted());
		entity.setInstitutionName(domain.getInstitutionName());
		entity.setSyncCursor(domain.getSyncCursor());
		entity.setStatus(domain.getStatus());
		entity.setErrorCode(domain.getErrorCode());
		return entity;
	}
}
