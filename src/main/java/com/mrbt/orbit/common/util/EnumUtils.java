package com.mrbt.orbit.common.util;

/**
 * Utility for null-safe enum conversions. Eliminates repeated
 * {@code enum != null ? enum.name() : null} pattern across mappers.
 */
public final class EnumUtils {

	private EnumUtils() {
	}

	public static <E extends Enum<E>> String toStringOrNull(E value) {
		return value != null ? value.name() : null;
	}

	public static <E extends Enum<E>> E fromStringOrNull(String value, Class<E> enumClass) {
		return value != null ? Enum.valueOf(enumClass, value) : null;
	}

}
