package com.mrbt.orbit.integration.core.port.in;

import java.util.Optional;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.integration.core.model.ExchangeRate;

public interface ExchangeRateUseCase extends UseCase {
	Optional<ExchangeRate> getLatestRate(String base, String target);
	void refreshRates();
}
