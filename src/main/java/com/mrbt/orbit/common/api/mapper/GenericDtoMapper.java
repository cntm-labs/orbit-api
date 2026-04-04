package com.mrbt.orbit.common.api.mapper;

import java.util.List;

/**
 * Base class for all DTO mappers in the API layer. Provides null-safe list
 * mapping to eliminate duplicate code across modules.
 *
 * @param <REQ>
 *            Request DTO type
 * @param <RES>
 *            Response DTO type
 * @param <D>
 *            Domain model type
 */
public abstract class GenericDtoMapper<REQ, RES, D> {

	public abstract D toDomain(REQ request);

	public abstract RES toResponse(D domain);

	public List<RES> toResponseList(List<D> domains) {
		if (domains == null) {
			return List.of();
		}
		return domains.stream().map(this::toResponse).toList();
	}

	public List<D> toDomainList(List<REQ> requests) {
		if (requests == null) {
			return List.of();
		}
		return requests.stream().map(this::toDomain).toList();
	}

}
