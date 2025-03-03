package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.myproject.sumup.notification_system.service.ProcessingException;

@Profile("dev")
@Service
public class MockEmailServiceImpl implements EmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailServiceImpl.class);

	@Override
	public void sendEmail(UUID messageId) throws ProcessingException {
		LOG.info("Mock send email for {}", messageId);
		
	}

}
