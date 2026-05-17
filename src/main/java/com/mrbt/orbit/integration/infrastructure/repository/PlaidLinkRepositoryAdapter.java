package com.mrbt.orbit.integration.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.core.port.out.PlaidLinkRepositoryPort;
import com.mrbt.orbit.integration.infrastructure.mapper.PlaidLinkEntityMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlaidLinkRepositoryAdapter implements PlaidLinkRepositoryPort {

	private final PlaidLinkRepository repository;
	private final PlaidLinkEntityMapper mapper;

	@Override
	public PlaidLink save(PlaidLink plaidLink) {
		return mapper.toDomain(repository.save(mapper.toEntity(plaidLink)));
	}

	@Override
	public Optional<PlaidLink> findById(UUID id) {
		return repository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<PlaidLink> findByUserId(UUID userId) {
		return mapper.toDomainList(repository.findByUserId(userId));
	}

	@Override
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}
}
