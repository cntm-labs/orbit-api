package com.mrbt.orbit.security.api.request;

public record UserPreferenceRequest(Boolean darkMode, String language, String dateFormat, String numberFormat,
		Boolean biometricEnabled, Boolean lab3dCharts, Boolean labAiSuggest, Boolean labSmartAlert) {
}
