package com.mrbt.orbit.ledger.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;

class TransactionTest {

	@Test
	void voidTransaction_shouldSetStatusToVoided() {
		Transaction tx = Transaction.builder().status(TransactionStatus.COMPLETED).build();
		tx.voidTransaction();
		assertThat(tx.getStatus()).isEqualTo(TransactionStatus.VOIDED);
	}

	@Test
	void isValidAmount_shouldReturnTrueForNonZero() {
		Transaction tx = Transaction.builder().amount(BigDecimal.TEN).build();
		assertThat(tx.isValidAmount()).isTrue();
	}

	@Test
	void isValidAmount_shouldReturnFalseForZero() {
		Transaction tx = Transaction.builder().amount(BigDecimal.ZERO).build();
		assertThat(tx.isValidAmount()).isFalse();
	}

	@Test
	void isValidAmount_shouldReturnFalseForNull() {
		Transaction tx = Transaction.builder().build();
		assertThat(tx.isValidAmount()).isFalse();
	}

	@Test
	void isCompleted_shouldReturnTrueForCompletedStatus() {
		Transaction tx = Transaction.builder().status(TransactionStatus.COMPLETED).build();
		assertThat(tx.isCompleted()).isTrue();
	}

	@Test
	void applyDefaults_shouldSetDefaultValues() {
		Transaction tx = Transaction.builder().build();
		tx.applyDefaults();
		assertThat(tx.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
		assertThat(tx.getIsReviewed()).isFalse();
		assertThat(tx.getExchangeRate()).isEqualTo(BigDecimal.ONE);
	}

}
