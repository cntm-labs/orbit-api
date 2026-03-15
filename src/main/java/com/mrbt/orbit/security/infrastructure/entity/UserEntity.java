package com.mrbt.orbit.security.infrastructure.entity;

import java.util.ArrayList;
import java.util.List;

import com.mrbt.orbit.common.infrastructure.entity.SoftDeletableEntity;
import com.mrbt.orbit.security.core.model.enums.UserStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends SoftDeletableEntity {

	@Column(unique = true)
	private String clerkUserId;

	@Column(unique = true)
	private String email;

	private String firstName;

	private String lastName;

	private String baseCurrency;

	private String timezone;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserAddressEntity> addresses = new ArrayList<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private UserPreferenceEntity preference;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private NotificationPreferenceEntity notificationPreference;

}
