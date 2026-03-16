package com.mrbt.orbit.audit.core.model;

import java.util.UUID;

import com.mrbt.orbit.audit.core.model.enums.NotificationChannel;
import com.mrbt.orbit.audit.core.model.enums.NotificationType;
import com.mrbt.orbit.common.core.model.BaseDomainModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Notification extends BaseDomainModel {

	private UUID userId;

	private NotificationType type;

	private String title;

	private String message;

	private NotificationChannel channel;

	private Boolean isRead;

}
