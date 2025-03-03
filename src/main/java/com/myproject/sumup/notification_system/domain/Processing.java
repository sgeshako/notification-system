package com.myproject.sumup.notification_system.domain;

import java.util.UUID;

import org.hibernate.annotations.SoftDelete;

import com.myproject.sumup.notification_system.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@SoftDelete
@Entity
public class Processing extends BaseEntity {

	@Id
	@GeneratedValue
	private UUID id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notification_message_id", nullable = false)
	private NotificationMessage message;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ProcessingStatus status;
	
	@Column(name = "details")
	private String details;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public NotificationMessage getMessage() {
		return message;
	}

	public void setMessage(NotificationMessage message) {
		this.message = message;
	}

	public ProcessingStatus getStatus() {
		return status;
	}

	public void setStatus(ProcessingStatus status) {
		this.status = status;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
