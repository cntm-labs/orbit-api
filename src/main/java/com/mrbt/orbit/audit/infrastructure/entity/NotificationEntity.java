package com.mrbt.orbit.audit.infrastructure.entity;

import java.util.UUID;

import com.mrbt.orbit.audit.core.model.enums.NotificationChannel;
import com.mrbt.orbit.audit.core.model.enums.NotificationType;
import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class NotificationEntity extends BaseEntity {

	@Column(nullable = false)
	private UUID userId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationType type;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String message;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NotificationChannel channel;

	@Column(nullable = false)
	private Boolean isRead;

}
