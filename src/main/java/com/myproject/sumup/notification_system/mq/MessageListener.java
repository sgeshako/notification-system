package com.myproject.sumup.notification_system.mq;

import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.PROCESSING_EMAIL_QUEUE;
import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.PROCESSING_SLACK_QUEUE;
import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.PROCESSING_SMS_QUEUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myproject.sumup.notification_system.domain.ProcessingStatus;
import com.myproject.sumup.notification_system.service.ProcessingException;
import com.myproject.sumup.notification_system.service.ProcessingService;
import com.myproject.sumup.notification_system.service.partner.EmailService;
import com.myproject.sumup.notification_system.service.partner.SlackService;
import com.myproject.sumup.notification_system.service.partner.SmsServiceImpl;

@Component
public class MessageListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);
	
	@Autowired
	private ProcessingService processingService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SlackService slackService;
	
	@Autowired
	private SmsServiceImpl smsService;

	@RabbitListener(queues = PROCESSING_EMAIL_QUEUE)
	public void receiveEmail(MessageId message) {
		LOG.debug("Just received an email message: {}", message.getMessageId());
		
		processingService.updateProcessingStatus(message.getMessageId(), ProcessingStatus.IN_PROGRESS);
		
		try {
			emailService.sendEmail(message.getMessageId());
			processingService.updateProcessingStatus(message.getMessageId(), ProcessingStatus.SEND_SUCCESSFUL);
		} catch (ProcessingException e) {
			processingService.updateProcessingStatus(
					message.getMessageId(), 
					ProcessingStatus.SEND_FAILURE,
					e.getCause().getMessage());
		}
	}
	
	@RabbitListener(queues = PROCESSING_SMS_QUEUE)
	public void receiveSms(MessageId message) {
		LOG.debug("Just received an sms message: {}", message.getMessageId());
		
		processingService.updateProcessingStatus(message.getMessageId(), ProcessingStatus.IN_PROGRESS);
		
		try {
			smsService.sendSmsMessage(message.getMessageId());
			processingService.updateProcessingStatus(message.getMessageId(), ProcessingStatus.SEND_SUCCESSFUL);
		} catch (Exception e) {
			processingService.updateProcessingStatus(
					message.getMessageId(), 
					ProcessingStatus.SEND_FAILURE,
					e.getCause().getMessage());
		}
	}
	
	@RabbitListener(queues = PROCESSING_SLACK_QUEUE)
	public void receiveSlack(MessageId message) {
		LOG.debug("Just received a slack message: {}", message.getMessageId());
		
		processingService.updateProcessingStatus(message.getMessageId(), ProcessingStatus.IN_PROGRESS);
		
		try {
			slackService.sendSlackMessage(message.getMessageId());
			processingService.updateProcessingStatus(message.getMessageId(), ProcessingStatus.SEND_SUCCESSFUL);
		} catch (ProcessingException e) {
			processingService.updateProcessingStatus(
					message.getMessageId(), 
					ProcessingStatus.SEND_FAILURE,
					e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
		}
	}
}
