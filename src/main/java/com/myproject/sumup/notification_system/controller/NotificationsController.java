package com.myproject.sumup.notification_system.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sumup.notification_system.domain.MessageChannel;
import com.myproject.sumup.notification_system.mq.MessagePublisher;
import com.myproject.sumup.notification_system.service.NotificationService;

import lombok.Data;

@RestController
public class NotificationsController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@PostMapping(path = "/email")
	public ResponseEntity<?> sendEmail() {
		
		UUID emailId = notificationService.registerEmail();
		
		messagePublisher.sendAsyncMessage(emailId, MessageChannel.EMAIL);
		
		return ResponseEntity.ok(Map.of("messageId", emailId));
	}
	
	@PostMapping(path = "/sms")
	public ResponseEntity<?> sendSms() {
		
		UUID smsId = notificationService.registerSms();
		
		messagePublisher.sendAsyncMessage(smsId, MessageChannel.SMS);
		
		return ResponseEntity.ok(Map.of("messageId", smsId));
	}
	
	@PostMapping(path = "/slack")
	public ResponseEntity<?> sendSlackMessage() {
		
		UUID slackMsgId = notificationService.registerSlack();
		
		messagePublisher.sendAsyncMessage(slackMsgId, MessageChannel.SLACK);
		
		return ResponseEntity.ok(Map.of("messageId", slackMsgId));

	}
	
	@Data
	public static class EmailInput {
		private String subject;
		private String body;
		private String recipient;
		private List<String> attachments;
		private List<String> cc;
	}
	
	@Data
	public static class SmsInput {
		private String recipient;
		private String body;
	}
	
	@Data
	public static class SlackInput {
		private String channel;
		private String message;
	}
}
