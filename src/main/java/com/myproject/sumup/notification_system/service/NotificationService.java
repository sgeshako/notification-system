package com.myproject.sumup.notification_system.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
//	@Autowired
//	private EmailDataRepository emailDataRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ProcessingRepository processingRepo;
	
	public UUID registerEmail() {
		NotificationMessage persistedMessage = insertNotificationMessage(MessageChannel.EMAIL);
		insertProcessingRecord(persistedMessage);
		
		EmailData data = new EmailData();
		data.setRecipient("whomever");
		data.setSubject("Whatever");
		data.setBody("Hello Email");
		data.setNotificationMessage(persistedMessage);
		entityManager.persist(data);
		
		return persistedMessage.getId();
	}
	
	public UUID registerSms() {
		NotificationMessage persistedMessage = insertNotificationMessage(MessageChannel.EMAIL);
		insertProcessingRecord(persistedMessage);
		
		SmsData data = new SmsData();
		data.setRecipient("+999999999");
		data.setBody("Hello SMS");
		data.setNotificationMessage(persistedMessage);
		entityManager.persist(data);
		
		return persistedMessage.getId();
	}
	
	public UUID registerSlack() {
		NotificationMessage persistedMessage = insertNotificationMessage(MessageChannel.EMAIL);
		insertProcessingRecord(persistedMessage);
		
		SlackMsgData data = new SlackMsgData();
		data.setChannel("#SlackChannel");
		data.setMessage("Hello everybody in Slack");
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
