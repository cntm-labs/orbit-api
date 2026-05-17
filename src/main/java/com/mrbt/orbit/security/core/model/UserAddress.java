package com.mrbt.orbit.security.core.model;

import java.util.UUID;

import com.mrbt.orbit.common.core.model.BaseDomainModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress extends BaseDomainModel {
	private UUID userId;
	private String label;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String postalCode;
	private String countryCode;
	private Boolean isDefault;
}
