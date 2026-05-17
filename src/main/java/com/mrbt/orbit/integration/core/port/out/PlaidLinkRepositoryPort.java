package com.mrbt.orbit.integration.core.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mrbt.orbit.integration.core.model.PlaidLink;

public interface PlaidLinkRepositoryPort {
	PlaidLink save(PlaidLink plaidLink);
	Optional<PlaidLink> findById(UUID id);
	List<PlaidLink> findByUserId(UUID userId);
	void deleteById(UUID id);
}
