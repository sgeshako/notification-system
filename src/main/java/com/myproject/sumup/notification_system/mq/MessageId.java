package com.myproject.sumup.notification_system.mq;

import java.util.UUID;

/**
 * JSON wrapper for message ID.
 */
public class MessageId {

	private UUID messageId;

	public MessageId(UUID messageId) {
		super();
		this.messageId = messageId;
	}

	public UUID getMessageId() {
		return messageId;
	}

	public void setMessageId(UUID messageId) {
		this.messageId = messageId;
	}
}
