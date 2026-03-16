package com.mrbt.orbit.audit.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.audit.core.model.Notification;
import com.mrbt.orbit.audit.core.port.out.NotificationRepositoryPort;
import com.mrbt.orbit.audit.infrastructure.entity.NotificationEntity;
import com.mrbt.orbit.audit.infrastructure.mapper.NotificationEntityMapper;
import com.mrbt.orbit.common.core.model.PageResult;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

	private final NotificationRepository springDataRepository;

	private final NotificationEntityMapper mapper;

	@Override
	public Notification save(Notification notification) {
		NotificationEntity entity = mapper.toEntity(notification);
		NotificationEntity saved = springDataRepository.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Notification> findById(UUID id) {
		return springDataRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public PageResult<Notification> findByUserId(UUID userId, int page, int size) {
		Page<NotificationEntity> entityPage = springDataRepository.findByUserIdOrderByCreatedAtDesc(userId,
				PageRequest.of(page, size));

		return new PageResult<>(mapper.toDomainList(entityPage.getContent()), entityPage.getTotalElements(),
				entityPage.getTotalPages(), entityPage.getNumber(), entityPage.getSize());
	}

	@Override
	public long countUnreadByUserId(UUID userId) {
		return springDataRepository.countByUserIdAndIsReadFalse(userId);
	}

	@Override
	@Transactional
	public void markAsReadById(UUID id) {
		springDataRepository.markAsReadById(id);
	}

	@Override
	@Transactional
	public void markAllAsReadByUserId(UUID userId) {
		springDataRepository.markAllAsReadByUserId(userId);
	}

}
