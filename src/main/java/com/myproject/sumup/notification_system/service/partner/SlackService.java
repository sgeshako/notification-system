package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import com.myproject.sumup.notification_system.service.ProcessingException;

public interface SlackService {

	void sendSlackMessage(UUID messageId) throws ProcessingException;
}
