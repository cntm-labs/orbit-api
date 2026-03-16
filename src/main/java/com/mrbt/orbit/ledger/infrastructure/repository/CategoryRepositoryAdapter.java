package com.mrbt.orbit.ledger.infrastructure.repository;

import com.mrbt.orbit.ledger.core.model.Category;
import com.mrbt.orbit.ledger.core.port.out.CategoryRepositoryPort;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

	private final CategoryRepository springDataRepository;
	private final CategoryMapper mapper;

	@Override
	public Category save(Category category) {
		CategoryEntity entity = mapper.toEntity(category);
		CategoryEntity savedEntity = springDataRepository.save(entity);
		return mapper.toDomain(savedEntity);
	}

	@Override
	public Optional<Category> findById(UUID id) {
		return springDataRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<Category> findSystemCategories() {
		return springDataRepository.findByIsSystemTrue().stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Category> findByUserId(UUID userId) {
		return springDataRepository.findByUserId(userId).stream().map(mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public boolean existsByUserIdAndName(UUID userId, String name) {
		return springDataRepository.existsByUserIdAndName(userId, name);
	}
}
