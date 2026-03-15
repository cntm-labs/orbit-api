package com.mrbt.orbit.security.core.model;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import com.mrbt.orbit.security.core.model.enums.UserStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseDomainModel {
	private String clerkUserId;
	private String email;
	private String firstName;
	private String lastName;
	private String baseCurrency;
	private String timezone;
	private UserStatus status;
}
