package com.mrbt.orbit.ledger.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.core.model.enums.RecurringTransactionStatus;

class RecurringTransactionTest {

	@Test
	void togglePause_shouldPauseActiveTransaction() {
		RecurringTransaction rt = RecurringTransaction.builder().status(RecurringTransactionStatus.ACTIVE).build();
		rt.togglePause();
		assertThat(rt.getStatus()).isEqualTo(RecurringTransactionStatus.PAUSED);
	}

	@Test
	void togglePause_shouldResumeFromPaused() {
		RecurringTransaction rt = RecurringTransaction.builder().status(RecurringTransactionStatus.PAUSED).build();
		rt.togglePause();
		assertThat(rt.getStatus()).isEqualTo(RecurringTransactionStatus.ACTIVE);
	}

	@Test
	void applyDefaults_shouldSetActiveStatusAndAutoConfirmTrue() {
		RecurringTransaction rt = RecurringTransaction.builder().build();
		rt.applyDefaults();
		assertThat(rt.getStatus()).isEqualTo(RecurringTransactionStatus.ACTIVE);
		assertThat(rt.getAutoConfirm()).isTrue();
	}

	@Test
	void cancel_shouldSetStatusToCancelled() {
		RecurringTransaction rt = RecurringTransaction.builder().status(RecurringTransactionStatus.ACTIVE).build();
		rt.cancel();
		assertThat(rt.getStatus()).isEqualTo(RecurringTransactionStatus.CANCELLED);
	}

}
