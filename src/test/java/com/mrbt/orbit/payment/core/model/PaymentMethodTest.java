package com.mrbt.orbit.payment.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.payment.core.model.enums.PaymentMethodStatus;

class PaymentMethodTest {

	@Test
	void applyDefaults_shouldSetActiveStatusAndNotDefault() {
		PaymentMethod pm = PaymentMethod.builder().build();
		pm.applyDefaults();
		assertThat(pm.getStatus()).isEqualTo(PaymentMethodStatus.ACTIVE);
		assertThat(pm.getIsDefault()).isFalse();
	}

	@Test
	void applyDefaults_shouldNotOverrideExistingValues() {
		PaymentMethod pm = PaymentMethod.builder().status(PaymentMethodStatus.EXPIRED).isDefault(true).build();
		pm.applyDefaults();
		assertThat(pm.getStatus()).isEqualTo(PaymentMethodStatus.EXPIRED);
		assertThat(pm.getIsDefault()).isTrue();
	}

}
