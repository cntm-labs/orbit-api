package com.mrbt.orbit.security.api.response;

public record UserPreferenceResponse(Boolean darkMode, String language, String dateFormat, String numberFormat,
		Boolean biometricEnabled, Boolean lab3dCharts, Boolean labAiSuggest, Boolean labSmartAlert) {
}
