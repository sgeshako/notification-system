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
	
	@Column(name = "in_queue_at")
	private LocalDateTime inQueueAt;
	
	@Column(name = "in_processing_at")
	private LocalDateTime inProcessingAt;
	
	@Column(name = "sent_ok_at")
	private LocalDateTime sentOkAt;
	
	@Column(name = "sending_failure_at")
	private LocalDateTime sendingFailureAt;

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

	public LocalDateTime getInQueueAt() {
		return inQueueAt;
	}

	public void setInQueueAt(LocalDateTime inQueueAt) {
		this.inQueueAt = inQueueAt;
	}

	public LocalDateTime getInProcessingAt() {
		return inProcessingAt;
	}

	public void setInProcessingAt(LocalDateTime inProcessingAt) {
		this.inProcessingAt = inProcessingAt;
	}

	public LocalDateTime getSentOkAt() {
		return sentOkAt;
	}

	public void setSentOkAt(LocalDateTime sentOkAt) {
		this.sentOkAt = sentOkAt;
	}

	public LocalDateTime getSendingFailureAt() {
		return sendingFailureAt;
	}

	public void setSendingFailureAt(LocalDateTime sendingFailureAt) {
		this.sendingFailureAt = sendingFailureAt;
	}

}
