package com.mrbt.orbit.payment.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.payment.core.model.enums.SubscriptionStatus;

class SubscriptionTest {

	@Test
	void togglePause_shouldPauseActiveSubscription() {
		Subscription subscription = Subscription.builder().status(SubscriptionStatus.ACTIVE).build();
		subscription.togglePause();
		assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.PAUSED);
	}

	@Test
	void togglePause_shouldResumeFromPaused() {
		Subscription subscription = Subscription.builder().status(SubscriptionStatus.PAUSED).build();
		subscription.togglePause();
		assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
	}

	@Test
	void applyDefaults_shouldSetActiveWhenStatusIsNull() {
		Subscription subscription = Subscription.builder().build();
		subscription.applyDefaults();
		assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
	}

	@Test
	void applyDefaults_shouldNotOverrideExistingStatus() {
		Subscription subscription = Subscription.builder().status(SubscriptionStatus.TRIAL).build();
		subscription.applyDefaults();
		assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.TRIAL);
	}

	@Test
	void cancel_shouldSetStatusToCancelled() {
		Subscription subscription = Subscription.builder().status(SubscriptionStatus.ACTIVE).build();
		subscription.cancel();
		assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.CANCELLED);
	}

}
