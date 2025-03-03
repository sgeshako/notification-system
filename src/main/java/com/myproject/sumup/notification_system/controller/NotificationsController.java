package com.myproject.sumup.notification_system.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.sumup.notification_system.controller.dto.EmailInput;
import com.myproject.sumup.notification_system.controller.dto.SlackInput;
import com.myproject.sumup.notification_system.controller.dto.SmsInput;
import com.myproject.sumup.notification_system.controller.validator.InputValidator;
import com.myproject.sumup.notification_system.domain.MessageChannel;
import com.myproject.sumup.notification_system.mq.MessagePublisher;
import com.myproject.sumup.notification_system.service.NotificationService;

@RestController
public class NotificationsController {

	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@PostMapping(path = "/email")
	public ResponseEntity<?> sendEmail(@RequestBody EmailInput input) {
		
		if (!InputValidator.validateEmail(input.getRecipient())) {
			return ResponseEntity.badRequest().body("Please provide valid email.");
		}
		
		UUID emailId = notificationService.registerEmail(input);
		
		messagePublisher.sendAsyncMessage(emailId, MessageChannel.EMAIL);
		
		return ResponseEntity.ok(Map.of("messageId", emailId));
	}
	
	@PostMapping(path = "/sms")
	public ResponseEntity<?> sendSms(@RequestBody SmsInput input) {
		
		if (!InputValidator.validatePhoneNumber(input.getRecipient())) {
			return ResponseEntity.badRequest().body("Please provide valid phone number format (+359XXXXXXXXX).");
		}
		
		UUID smsId = notificationService.registerSms(input);
		
		messagePublisher.sendAsyncMessage(smsId, MessageChannel.SMS);
		
		return ResponseEntity.ok(Map.of("messageId", smsId));
	}
	
	@PostMapping(path = "/slack")
	public ResponseEntity<?> sendSlackMessage(@RequestBody SlackInput input) {
		
		if (!InputValidator.validateSlackChannel(input.getChannel())) {
			return ResponseEntity.badRequest().body("Please provide valid Slack channel (C1234567890).");
		}
		
		UUID slackMsgId = notificationService.registerSlack(input);
		
		messagePublisher.sendAsyncMessage(slackMsgId, MessageChannel.SLACK);
		
		return ResponseEntity.ok(Map.of("messageId", slackMsgId));

	}
	
}
