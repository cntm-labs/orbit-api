package com.mrbt.orbit.security.api.response;

import java.math.BigDecimal;

public record NotificationPreferenceResponse(Boolean emailEnabled, Boolean pushEnabled, Boolean budgetAlerts,
		Boolean billReminders, BigDecimal largeTransactionThreshold) {
}
