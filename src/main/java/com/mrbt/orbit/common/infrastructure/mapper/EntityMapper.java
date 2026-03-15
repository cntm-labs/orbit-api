package com.mrbt.orbit.common.infrastructure.mapper;

import java.util.List;

/**
 * Generic interface for mapping between Entities and Domain Models.
 *
 * @param <E>
 *            Entity type (Infrastructure layer)
 * @param <D>
 *            Domain Model type (Core layer)
 */
public interface EntityMapper<E, D> {
	D toDomain(E entity);
	E toEntity(D domain);
	List<D> toDomainList(List<E> entities);
	List<E> toEntityList(List<D> domains);
}
