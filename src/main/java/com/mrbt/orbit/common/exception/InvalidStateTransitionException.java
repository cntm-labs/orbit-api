package com.mrbt.orbit.common.exception;

public class InvalidStateTransitionException extends DomainException {

	public InvalidStateTransitionException(String entityName, String currentState, String targetState) {
		super(String.format("Cannot transition %s from %s to %s", entityName, currentState, targetState));
	}

}
