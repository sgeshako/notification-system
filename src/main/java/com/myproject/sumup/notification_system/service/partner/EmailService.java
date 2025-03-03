package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import com.myproject.sumup.notification_system.service.ProcessingException;

public interface EmailService {

	void sendEmail(UUID messageId) throws ProcessingException;
}
