package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import com.myproject.sumup.notification_system.service.ProcessingException;

public interface EmailService {

	
	/**
	 * Send actual email for the given Message.
	 *  
	 * @param messageId id of the Message.
	 * @throws ProcessingException if no email data is found or sending of email fails.
	 */
	void sendEmail(UUID messageId) throws ProcessingException;
}
