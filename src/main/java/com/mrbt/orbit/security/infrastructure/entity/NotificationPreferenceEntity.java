package com.mrbt.orbit.security.infrastructure.entity;

import java.math.BigDecimal;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
@NoArgsConstructor
public class NotificationPreferenceEntity extends BaseEntity {

	private Boolean emailEnabled;

	private Boolean pushEnabled;

	private Boolean budgetAlerts;

	private Boolean billReminders;

	@Column(precision = 19, scale = 4)
	private BigDecimal largeTransactionThreshold;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserEntity user;

}
