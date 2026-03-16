package com.mrbt.orbit.audit.infrastructure.adapter;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.mrbt.orbit.audit.core.model.Notification;
import com.mrbt.orbit.audit.core.port.out.NotificationBroadcastPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StompNotificationBroadcaster implements NotificationBroadcastPort {

	private final SimpMessagingTemplate messagingTemplate;

	@Override
	public void broadcast(Notification notification) {
		String destination = "/topic/notifications/" + notification.getUserId();
		messagingTemplate.convertAndSend(destination, notification);
	}

}
