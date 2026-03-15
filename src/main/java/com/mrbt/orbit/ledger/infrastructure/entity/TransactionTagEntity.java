package com.mrbt.orbit.ledger.infrastructure.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction_tags")
@Getter
@Setter
@NoArgsConstructor
public class TransactionTagEntity {

	@EmbeddedId
	private TransactionTagId id;

	@MapsId("transactionId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id")
	private TransactionEntity transaction;

	@MapsId("tagId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private TagEntity tag;

}
