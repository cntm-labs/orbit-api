package com.mrbt.orbit.ledger.infrastructure.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TransactionTagId implements Serializable {

	private UUID transactionId;

	private UUID tagId;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TransactionTagId that = (TransactionTagId) o;
		return Objects.equals(transactionId, that.transactionId) && Objects.equals(tagId, that.tagId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(transactionId, tagId);
	}

}
