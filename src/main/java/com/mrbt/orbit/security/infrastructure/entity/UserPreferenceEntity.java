package com.mrbt.orbit.security.infrastructure.entity;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
public class UserPreferenceEntity extends BaseEntity {

	private Boolean darkMode;

	private String language;

	private String dateFormat;

	private String numberFormat;

	private Boolean biometricEnabled;

	private Boolean lab3dCharts;

	private Boolean labAiSuggest;

	private Boolean labSmartAlert;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private UserEntity user;

}
