package com.myproject.sumup.notification_system.mq;

import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.PROCESSING_EMAIL_QUEUE;
import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.PROCESSING_SLACK_QUEUE;
import static com.myproject.sumup.notification_system.mq.RabbitMqConfig.PROCESSING_SMS_QUEUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MessageListener.class);

	@RabbitListener(queues = PROCESSING_EMAIL_QUEUE)
	public void receiveEmail(MessageId message) {
		LOG.info("Just received an email message: {}", message.getMessageId());
	}
	
	@RabbitListener(queues = PROCESSING_SMS_QUEUE)
	public void receiveSms(MessageId message) {
		LOG.info("Just received an sms message: {}", message.getMessageId());
	}
	
	@RabbitListener(queues = PROCESSING_SLACK_QUEUE)
	public void receiveSlack(MessageId message) {
		LOG.info("Just received a slack message: {}", message.getMessageId());
	}
}
