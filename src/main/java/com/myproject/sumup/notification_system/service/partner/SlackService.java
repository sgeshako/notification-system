package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import com.myproject.sumup.notification_system.service.ProcessingException;

public interface SlackService {

	/**
	 * Send actual Slack message for the given Message.
	 * 
	 * @param messageId id of the Message.
	 * @throws ProcessingException if no slack message data is found or sending of slack message fails.
	 */
	void sendSlackMessage(UUID messageId) throws ProcessingException;
}
