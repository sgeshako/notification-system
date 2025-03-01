package com.myproject.sumup.notification_system.domain.data;

import java.util.UUID;

import com.myproject.sumup.notification_system.domain.NotificationMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class SmsData {

	@Id
	private UUID id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private NotificationMessage notificationMessage;
	
	@Column(nullable = false)
	private String recipient;
	
	@Column(nullable = false)
	private String body;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public NotificationMessage getNotificationMessage() {
		return notificationMessage;
	}

	public void setNotificationMessage(NotificationMessage notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
