package com.mrbt.orbit.payment.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.SoftDeletableEntity;
import com.mrbt.orbit.payment.core.model.enums.BillingCycle;
import com.mrbt.orbit.payment.core.model.enums.SubscriptionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionEntity extends SoftDeletableEntity {

	@Column(nullable = false)
	private UUID userId;

	private UUID categoryId;

	private UUID paymentMethodId;

	@Column(nullable = false)
	private UUID accountId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal amount;

	@Column(nullable = false)
	private String currencyCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BillingCycle billingCycle;

	private LocalDate nextBillingDate;

	private Integer reminderDaysBefore;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubscriptionStatus status;

	private LocalDate trialEndDate;

}
