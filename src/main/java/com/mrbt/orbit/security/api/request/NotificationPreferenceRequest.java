package com.mrbt.orbit.security.api.request;

import java.math.BigDecimal;

public record NotificationPreferenceRequest(Boolean emailEnabled, Boolean pushEnabled, Boolean budgetAlerts,
		Boolean billReminders, BigDecimal largeTransactionThreshold) {
}
