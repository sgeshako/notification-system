package com.myproject.sumup.notification_system.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.myproject.sumup.notification_system.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class NotificationMessage extends BaseEntity {

	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(name = "channel", nullable = false)
	@Enumerated(EnumType.STRING)
	private MessageChannel channel;
	
	@Column(name = "arrived_at", nullable = false)
	private LocalDateTime arrivedAt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public MessageChannel getChannel() {
		return channel;
	}

	public void setChannel(MessageChannel channel) {
		this.channel = channel;
	}

	public LocalDateTime getArrivedAt() {
		return arrivedAt;
	}

	public void setArrivedAt(LocalDateTime arrivedAt) {
		this.arrivedAt = arrivedAt;
	}

}
