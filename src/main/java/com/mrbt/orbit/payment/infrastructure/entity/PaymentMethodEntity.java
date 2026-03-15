package com.mrbt.orbit.payment.infrastructure.entity;

import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.SoftDeletableEntity;
import com.mrbt.orbit.payment.core.model.enums.PaymentMethodStatus;
import com.mrbt.orbit.payment.core.model.enums.PaymentProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payment_methods")
@Getter
@Setter
@NoArgsConstructor
public class PaymentMethodEntity extends SoftDeletableEntity {

	@Column(nullable = false)
	private UUID userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentProvider provider;

	private String providerReferenceId;

	private String lastFourDigits;

	@Column(nullable = false)
	private Boolean isDefault;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethodStatus status;

}
