package com.mrbt.orbit.integration.core.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.integration.core.model.PlaidLink;
import com.mrbt.orbit.integration.core.model.enums.PlaidLinkStatus;
import com.mrbt.orbit.integration.core.port.in.PlaidLinkUseCase;
import com.mrbt.orbit.integration.core.port.out.PlaidLinkRepositoryPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaidLinkService implements PlaidLinkUseCase {

	private final PlaidLinkRepositoryPort repositoryPort;

	@Override
	@Transactional
	public PlaidLink createLink(UUID userId, String publicToken) {
		log.info("Creating Plaid link for user: {} with token: {}", userId, publicToken);
		// In a real implementation, this would exchange publicToken for accessToken via
		// Plaid API
		PlaidLink plaidLink = PlaidLink.builder().userId(userId).plaidItemId("plaid-item-" + UUID.randomUUID()) // Mock
																												// item
																												// ID
				.accessTokenEncrypted("encrypted-token-placeholder").status(PlaidLinkStatus.ACTIVE).build();

		return repositoryPort.save(plaidLink);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PlaidLink> getLinksByUser(UUID userId) {
		return repositoryPort.findByUserId(userId);
	}

	@Override
	@Transactional
	public void syncTransactions(UUID linkId) {
		PlaidLink link = repositoryPort.findById(linkId)
				.orElseThrow(() -> new ResourceNotFoundException("PlaidLink", "id", linkId));

		log.info("Syncing transactions for Plaid link: {}", link.getId());
		// Implement Plaid sync logic here
	}

	@Override
	@Transactional
	public void disconnectLink(UUID linkId) {
		repositoryPort.deleteById(linkId);
		log.info("Disconnected Plaid link: {}", linkId);
	}
}
