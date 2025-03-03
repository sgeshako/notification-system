package com.myproject.sumup.notification_system.service.partner;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.sumup.notification_system.domain.data.EmailData;
import com.myproject.sumup.notification_system.repository.EmailDataRepository;
import com.myproject.sumup.notification_system.service.ProcessingException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Profile({ "prod", "local" })
@Transactional
@Service
public class EmailServiceImpl implements EmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailDataRepository emailDataRepo;

	@Override
	public void sendEmail(UUID messageId) throws ProcessingException {

		EmailData emailData = emailDataRepo.findById(messageId)
				.orElseThrow(() -> new ProcessingException("No email data found for messageId " + messageId));
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom("someguy@example.com");
		    message.setRecipients(MimeMessage.RecipientType.TO, emailData.getRecipient());
		    message.setSubject(emailData.getSubject());
			message.setContent(emailData.getBody(), "text/html; charset=utf-8");

			mailSender.send(message);
		} catch (MessagingException e) {
			LOG.error("Could not build message", e);
			throw new ProcessingException("Could not build message", e);
		} catch (RuntimeException e) {
			LOG.error("Failure to send email", e);
			throw new ProcessingException("Failure to send email", e);
		}

	}
}
