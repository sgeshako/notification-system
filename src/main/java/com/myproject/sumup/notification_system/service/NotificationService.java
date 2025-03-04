package com.myproject.sumup.notification_system.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.sumup.notification_system.controller.dto.EmailInput;
import com.myproject.sumup.notification_system.controller.dto.SlackInput;
import com.myproject.sumup.notification_system.controller.dto.SmsInput;
import com.myproject.sumup.notification_system.domain.MessageChannel;
import com.myproject.sumup.notification_system.domain.NotificationMessage;
import com.myproject.sumup.notification_system.domain.Processing;
import com.myproject.sumup.notification_system.domain.ProcessingStatus;
import com.myproject.sumup.notification_system.domain.data.EmailData;
import com.myproject.sumup.notification_system.domain.data.SlackMsgData;
import com.myproject.sumup.notification_system.domain.data.SmsData;
import com.myproject.sumup.notification_system.repository.NotificationMessageRepository;
import com.myproject.sumup.notification_system.repository.ProcessingRepository;

import jakarta.persistence.EntityManager;

@Transactional
@Service
public class NotificationService {

	@Autowired
	private NotificationMessageRepository notificationMessageRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ProcessingRepository processingRepo;
	
	/**
	 * Save email data and create a message ID for this email.
	 * 
	 * @param input the email data
	 * @return the generated message ID
	 */
	public UUID registerEmail(EmailInput input) {
		NotificationMessage persistedMessage = insertNotificationMessage(MessageChannel.EMAIL);
		insertProcessingRecord(persistedMessage);
		
		EmailData data = new EmailData();
		data.setRecipient(input.getRecipient());
		data.setSubject(input.getSubject());
		data.setBody(input.getBody());
		data.setNotificationMessage(persistedMessage);
		entityManager.persist(data);
		
		return persistedMessage.getId();
	}
	
	/**
	 * Save SMS data and create a message ID for this SMS.
	 * 
	 * @param input the SMS data
	 * @return the generated message ID
	 */
	public UUID registerSms(SmsInput input) {
		NotificationMessage persistedMessage = insertNotificationMessage(MessageChannel.EMAIL);
		insertProcessingRecord(persistedMessage);
		
		SmsData data = new SmsData();
		data.setRecipient(input.getRecipient());
		data.setBody(input.getBody());
		data.setNotificationMessage(persistedMessage);
		entityManager.persist(data);
		
		return persistedMessage.getId();
	}
	
	/**
	 * Save Slack message data and create a message ID.
	 * 
	 * @param input the Slack message data
	 * @return the generated message ID
	 */
	public UUID registerSlack(SlackInput input) {
		NotificationMessage persistedMessage = insertNotificationMessage(MessageChannel.EMAIL);
		insertProcessingRecord(persistedMessage);
		
		SlackMsgData data = new SlackMsgData();
		data.setChannel(input.getChannel());
		data.setMessage(input.getMessage());
		data.setNotificationMessage(persistedMessage);
		entityManager.persist(data);
		
		return persistedMessage.getId();
	}
	
	private void insertProcessingRecord(NotificationMessage message) {
		
		Processing processing = new Processing();
		processing.setStatus(ProcessingStatus.ARRIVED);
		processing.setMessage(message);
		processingRepo.save(processing);
	}
	
	private NotificationMessage insertNotificationMessage(MessageChannel channel) {
		
		NotificationMessage message = new NotificationMessage();
		message.setArrivedAt(LocalDateTime.now());
		message.setChannel(channel);
		return notificationMessageRepo.save(message);
	}
}
