package com.mrbt.orbit.security.core.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.mrbt.orbit.common.core.model.BaseDomainModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference extends BaseDomainModel {
	private UUID userId;
	private Boolean emailEnabled;
	private Boolean pushEnabled;
	private Boolean budgetAlerts;
	private Boolean billReminders;
	private BigDecimal largeTransactionThreshold;
}
