package com.mrbt.orbit.security.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.port.out.UserAddressRepositoryPort;
import com.mrbt.orbit.security.infrastructure.mapper.UserAddressMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAddressRepositoryAdapter implements UserAddressRepositoryPort {

	private final UserAddressRepository repository;
	private final UserAddressMapper mapper;

	@Override
	public UserAddress save(UserAddress address) {
		return mapper.toDomain(repository.save(mapper.toEntity(address)));
	}

	@Override
	public Optional<UserAddress> findById(UUID id) {
		return repository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<UserAddress> findByUserId(UUID userId) {
		return mapper.toDomainList(repository.findByUserId(userId));
	}

	@Override
	public void deleteById(UUID id) {
		repository.deleteById(id);
	}
}
