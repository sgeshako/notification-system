package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl {

	private static final Logger LOG = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	public void sendSmsMessage(UUID messageId) {
		LOG.info("Sending sms for {}", messageId);
	}
}
