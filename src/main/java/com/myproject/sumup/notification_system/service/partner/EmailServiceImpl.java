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

@Profile("prod")
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
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(to);
//		message.setSubject(subject);
//		message.setText(body);
		EmailData emailData = emailDataRepo.findById(messageId).orElseThrow();
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom("someguy@example.com");
		    message.setRecipients(MimeMessage.RecipientType.TO, "test.user@gmail.com");
		    message.setSubject(emailData.getSubject());
			String htmlContent = "<h1>This is a test VERY IMPORTANT email</h1>" +
	                "<p>It can contain <strong>HTML</strong> content.</p>" + 
					"<p>Data in this email uses <strong>" + emailData.getBody() + "</strong> from the DB.</p>";
				message.setContent(htmlContent, "text/html; charset=utf-8");

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
