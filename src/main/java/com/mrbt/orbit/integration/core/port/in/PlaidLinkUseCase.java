package com.mrbt.orbit.integration.core.port.in;

import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.integration.core.model.PlaidLink;

public interface PlaidLinkUseCase extends UseCase {
	PlaidLink createLink(UUID userId, String publicToken);
	List<PlaidLink> getLinksByUser(UUID userId);
	void syncTransactions(UUID linkId);
	void disconnectLink(UUID linkId);
}
