package com.mrbt.orbit.security.infrastructure.entity;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_addresses")
@Getter
@Setter
@NoArgsConstructor
public class UserAddressEntity extends BaseEntity {

	private String label;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String state;

	private String postalCode;

	private String countryCode;

	private Boolean isDefault;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

}
