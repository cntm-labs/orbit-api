package com.mrbt.orbit.audit.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mrbt.orbit.audit.core.model.enums.AuditAction;

/**
 * Annotation to mark methods for automated audit logging.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
	/**
	 * The action being performed (e.g., CREATE, UPDATE, DELETE).
	 */
	AuditAction action();

	/**
	 * The type of entity being affected (e.g., ACCOUNT, TRANSACTION).
	 */
	String entityType() default "";
}
