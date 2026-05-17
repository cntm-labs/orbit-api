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
public class UserPreference extends BaseDomainModel {
	private UUID userId;
	private Boolean darkMode;
	private String language;
	private String dateFormat;
	private String numberFormat;
	private Boolean biometricEnabled;
	private Boolean lab3dCharts;
	private Boolean labAiSuggest;
	private Boolean labSmartAlert;
}
